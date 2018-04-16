import cv2
import numpy as np
import os
import matplotlib.pyplot as plt
import csv
from skimage.feature import hog


#################################################### Main Variables #########################################################
DistanceAcceptedThreshold = 2.3
sharpnessOfCorners = .45
directories = ["./Diamond_TS/", "./Line_TS/", "./Ellipse_TS/"]
diamondType = 1
lineType = 2
ellipseType = 3
csvFileName = 'features.csv'
csvFileDelimiter = ','
#################################################### Helping Functions #############################################################


def remove_empty(my_list):
	new_list = []
	for element in my_list:
		if len(element.lstrip().rstrip()) > 0:
			new_list.append(element.rstrip().lstrip())
	return new_list


def dispImageWithPoints(img,points, borders=[]):
	# Displaying image with corners
	out = img
	for point in points:
		if "ravel" in dir(point):
			x, y = point.ravel()
		else:
			x, y = point
		cv2.circle(out,(x,y),3,255,-1)
	if len(borders) > 0:
		cv2.line(out, borders[0], borders[1], 100, 10)
	cv2.imshow('output',out)


def dispImageWithLines(img,lines):
	for rho,theta in lines[0]:
		a = np.cos(theta)
		b = np.sin(theta)
		x0 = a*rho
		y0 = b*rho
		x1 = int(x0 + 1000*(-b))
		y1 = int(y0 + 1000*(a))
		x2 = int(x0 - 1000*(-b))
		y2 = int(y0 - 1000*(a))
		cv2.line(img,(x1,y1),(x2,y2),(0,0,255),2)
	cv2.imshow('output',img)


def showHist(arr):
	plt.hist(arr)
	fig = plt.gcf()
	fig.show()


def getPoints(rows, columns, number, its_column = True):
	# if true it's a column index, else it's a row
	if its_column:
		indices = np.nonzero(columns == number)
	else:
		indices = np.nonzero(rows == number)
	middle = indices[0][len(indices[0])/2]
	index = columns[middle], rows[middle]
	return index


def subtractTuples(tuple1 ,tuple2):
	return [tuple1[i]-tuple2[i] for i in range(len(tuple1))]


def getSumOfDistances(p1, p2, list_of_points):
	# find distance between all the points in the list and the line between P1 and P2
	distances = 0.0
	den = cv2.norm(np.asarray(p1), np.asarray(p2))
	if den == 0:
		return 0
	for p3 in list_of_points:
		distances += (cv2.norm(np.cross(subtractTuples(p2, p1), subtractTuples(p1, p3))) / den)
	return distances


def getPointsOnLine(p1, p2, list_of_points):
	num = 0
	den = cv2.norm(np.asarray(p1), np.asarray(p2))
	if den == 0:
		return 0
	for p3 in list_of_points:
		 if cv2.norm(np.cross(subtractTuples(p2, p1), subtractTuples(p1, p3))) / den == 0:
			 num += 1
	return num


def getActualAndExtremePoints(img):
	gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	gray = np.int0(gray)  # image is rows * columns
	rows, columns = np.nonzero(gray)  # this will return two arrays for non-zero elements in the image, the first is for rows and the second is for columns
	leftmost = getPoints(rows, columns, columns.min())
	rightmost = getPoints(rows, columns, columns.max())
	upper = getPoints(rows, columns, rows.min(), False)
	lower = getPoints(rows, columns, rows.max(), False)
	return leftmost, rightmost, upper, lower, rows, columns


def pointsOnline(p1, p2, listOfPoints):
	den = cv2.norm(np.asarray(p1), np.asarray(p2))
	if den == 0:
		return 0
	num = 0
	# newlst = []
	for p3 in listOfPoints:
		if (cv2.norm(np.cross(subtractTuples(p2, p1), subtractTuples(p1, p3))) / den) < DistanceAcceptedThreshold:
			num += 1
			# newlst.append(p3)
	# dispImageWithPoints(img, listOfPoints, [p1, p2])
	return num


def betweenTwoPoints(minX, minY, maxX, maxY, point):
	return minX < point[0] < maxX and minY < point[1] < maxY


def getFeaturesOfCertainType(Type):
	freqs = {}
	dir = os.listdir(directories[Type-1])
	for path in dir:
		# reading image
		if path.endswith('.bmp'):
			img = cv2.imread(directories[Type-1] + path)
			imgFeatureList = [getNumberOfCorners(img), getAverageOfAlignedPoints(img), getAverageDistance(img), getNumberOfLines(img)]
			imgFeatureList = np.concatenate((imgFeatureList, getHog(img)), axis=0)
			freqs[path[:-4]] = (imgFeatureList, Type)
	return freqs


#################################################### Main Functions #############################################################

def getHog(image):
	image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
	image = cv2.resize(image, (0,0), fx=0.5, fy=0.5) 
	fd, _ = hog(image, orientations=8, pixels_per_cell =(16, 16),cells_per_block=(1, 1), visualise=True,feature_vector= True)
	return fd

def getNumberOfCorners(img):
	# Corner feature
	gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
	gray = np.float32(gray)
	corners = cv2.goodFeaturesToTrack(gray,100, sharpnessOfCorners,50) # number of Features,quality,minimum distance
	# dispImageWithPoints(img, corners)
	corners = np.int0(corners)
	numberCorners = np.shape(corners)[0]
	return numberCorners


def getSegmentsOfPoints(rows, columns, leftmost, rightmost, upper, lower):
	# between upper and rightmost
	rightUpperLine = [(columns[i], rows[i]) for i in range(len(columns)) if
		   betweenTwoPoints(minX=upper[0], minY=upper[1], maxX=rightmost[0], maxY=rightmost[1],
							point=(columns[i], rows[i]))]
	# between upper and leftmost
	leftUpperLine = [(columns[i], rows[i]) for i in range(len(columns)) if
		   betweenTwoPoints(minX=leftmost[0], minY=upper[1], maxX=upper[0], maxY=leftmost[1],
							point=(columns[i], rows[i]))]
	# between leftmost and lower
	leftLowerLine = [(columns[i], rows[i]) for i in range(len(columns)) if
		   betweenTwoPoints(minX=leftmost[0], minY=leftmost[1], maxX=lower[0], maxY=lower[1],
							point=(columns[i], rows[i]))]
	# between lower and rightmost
	rightLowerLine = [(columns[i], rows[i]) for i in range(len(columns)) if
		   betweenTwoPoints(minX=lower[0], minY=rightmost[1], maxX=rightmost[0], maxY=lower[1],
							point=(columns[i], rows[i]))]
	return rightUpperLine, leftUpperLine, leftLowerLine, rightLowerLine


def getAverageOfAlignedPoints(img):
	leftmost, rightmost, upper, lower, rows, columns = getActualAndExtremePoints(img)
	rightUpperLine, leftUpperLine, leftLowerLine, rightLowerLine = getSegmentsOfPoints(rows, columns, leftmost,
																					   rightmost, upper, lower)
	# between rightmost and upper
	numberOfPoints = pointsOnline(rightmost, upper, rightUpperLine)
	# between leftmost and upper
	numberOfPoints += pointsOnline(leftmost, upper, leftUpperLine)
	# between leftmost and lower
	numberOfPoints += pointsOnline(leftmost, lower, leftLowerLine)
	# between rightmost and lower
	numberOfPoints += pointsOnline(rightmost, lower, rightLowerLine)
	if numberOfPoints == 0:
		return len(columns)*1000
	return numberOfPoints / float(len(columns))  


def getAverageDistance(img):
	leftmost, rightmost, upper, lower, rows, columns = getActualAndExtremePoints(img)
	rightUpperLine, leftUpperLine, leftLowerLine, rightLowerLine = getSegmentsOfPoints(rows, columns, leftmost,
																					   rightmost, upper, lower)
	# between rightmost and upper
	distance = getSumOfDistances(rightmost, upper, rightUpperLine)
	# between leftmost and upper
	distance += getSumOfDistances(leftmost, upper, leftUpperLine)
	# between leftmost and lower
	distance += getSumOfDistances(leftmost, lower, leftLowerLine)
	# between rightmost and lower
	distance += getSumOfDistances(rightmost, lower, rightLowerLine)
	return distance / len(columns)


def getNumberOfLines(img):
	#getting lines
	gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
	edges = cv2.Canny(gray,50,150,apertureSize = 3)
	lines = cv2.HoughLines(edges,1,(np.pi)/180,40)
	if lines is None:
		return 0
	else:
		return np.shape(lines)[1]


def addToCSV(featuresDic):
	with open(csvFileName, 'ab') as csvFile:
		writer = csv.writer(csvFile, delimiter=csvFileDelimiter)
		for img in featuresDic.keys():
			writer.writerow(np.append(featuresDic[img][0] , [featuresDic[img][1]]))


#################################################### The Main #############################################################


if __name__ == "__main__":
	# list will contain a list for each image with the four features
	diamondsFreqs = {}
	linesFreqs = {}
	ellipsesFreqs = {}

	print("Diamonds....")
	diamondsFreqs = getFeaturesOfCertainType(diamondType)
	addToCSV(diamondsFreqs)
	print("Lines....")
	linesFreqs = getFeaturesOfCertainType(lineType)
	addToCSV(linesFreqs)
	print("Ellipses....")
	ellipsesFreqs = getFeaturesOfCertainType(ellipseType)
	addToCSV(ellipsesFreqs)
