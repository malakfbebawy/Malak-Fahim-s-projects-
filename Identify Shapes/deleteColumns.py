import pandas as pd

df = pd.read_csv("features.csv",header = None)
pd.set_option("display.max_columns", len(df.columns))

df = df.loc[:, (df != 0).any(axis=0)]

df.to_csv("new_features.csv",header = None)