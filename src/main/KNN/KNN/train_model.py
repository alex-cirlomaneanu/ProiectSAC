import pandas as pd
from sklearn.pipeline import Pipeline
from sklearn.base import BaseEstimator, TransformerMixin
from sklearn.neighbors import NearestNeighbors
from joblib import dump

from transformers import RemoveColumnsTransformer, \
                            OHETransformer, \
                            BinaryTransformer, \
                            StringToIntTransformer, \
                            LowerCaseTransformer, \
                            Word2VecNameTransformer, \
                            StandardScalerTransformer, \
                            KnnModel

df = pd.read_csv("./output_WITHOUT_DUPLICATES.csv")

important_columns = ['brand', 
                     'model', 
                     'gearbox', 
                     'body_type',
                     'price', 
                     'km', 
                     'year',
                     'fuel_type', 
                     'transmission', 
                     'power', 
                     'cylinder_capacity',
                     'color', 
                     'vehicle_condition']

pipe = Pipeline(
    steps = [
        ('select_columns', RemoveColumnsTransformer(important_columns)),
        ('1_hot_body_type', OHETransformer('body_type')),
        ('1_hot_fuel_type', OHETransformer('fuel_type')),
        ('1_hot_color', OHETransformer('color')),
        ('1_hot_transmission', OHETransformer('transmission')),
        ('binary_vehicle_condition', BinaryTransformer('vehicle_condition', {'Nou': 1, 'Second hand': 0})),
        ('binary_gearbox', BinaryTransformer('gearbox', {'Automata': 1, 'Manuala': 0})),
        ('int_km', StringToIntTransformer('km')),
        ('int_price', StringToIntTransformer('price')),
        ('int_power', StringToIntTransformer('power')),
        ('int_cylinder_capacity', StringToIntTransformer('cylinder_capacity')),
        ('name_creation', LowerCaseTransformer()),
        ('name_transformer', Word2VecNameTransformer(['brand', 'model'])),
        ('scaler', StandardScalerTransformer()),
        ('knn_model', KnnModel(20))
    ])

pipe.fit(df)

dump(pipe, 'knn_pipeline.joblib')