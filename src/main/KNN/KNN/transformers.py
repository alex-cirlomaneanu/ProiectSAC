import pandas as pd
from sklearn.base import BaseEstimator, TransformerMixin
from sklearn.preprocessing import OneHotEncoder
from gensim.models import Word2Vec
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import NearestNeighbors

class RemoveColumnsTransformer(BaseEstimator, TransformerMixin):
    def __init__(self, columns):
        # save the features list internally in the class
        self.columns = columns
        
    def fit(self, X, y = None):
        return self    
    
    def transform(self, X, y = None):
        # return the dataframe with the specified features
        return X[self.columns]
    

class OHETransformer(BaseEstimator, TransformerMixin):
    def __init__(self, column):
        # save the features list internally in the class
        self.column = column
        self.one_hot_encoder = OneHotEncoder(sparse_output=False, handle_unknown='ignore')
        self.categories_ = None  # Store the categories during fit

        
    def fit(self, X, y = None):
        self.one_hot_encoder.fit(X[[self.column]])
        self.categories_ = self.one_hot_encoder.categories_
        return self
    
    def transform(self, X, y = None):
                # Transform the test data using the categories from the training data
        one_hot_encoded = pd.DataFrame(
            self.one_hot_encoder.transform(X[[self.column]]),
            columns=self.one_hot_encoder.get_feature_names_out([self.column])
        )

        one_hot_encoded.index = X.index
        
        # Concatenate the one-hot encoded columns with the original DataFrame
        X = pd.concat([X, one_hot_encoded], axis=1)

        X = X.drop(columns = [self.column])
        return X
    
class BinaryTransformer(BaseEstimator, TransformerMixin):
    def __init__(self, column, translations):
        # save the features list internally in the class
        self.column = column
        self.translations = translations
        
    def fit(self, X, y = None):
        return self    
    
    def transform(self, X, y = None):
        # return the dataframe with the specified features
        X[self.column] = X[self.column].map(self.translations)
        return X
    
class StringToIntTransformer(BaseEstimator, TransformerMixin):
    def __init__(self, column):
        # save the features list internally in the class
        self.column = column
        
    def fit(self, X, y = None):
        return self    
    
    def transform(self, X, y = None):
        # return the dataframe with the specified features
        X[self.column] = X[self.column].str.replace(r'm3', '', regex=True).fillna(0)
        X[self.column] = X[self.column].str.replace(r'[^0-9]', '', regex=True).fillna(0).astype(int)
        return X
    
class LowerCaseTransformer(BaseEstimator, TransformerMixin):
    def __init__(self, column = None):
        # save the features list internally in the class
        self.column = column
        
    def fit(self, X, y = None):
        return self    
    
    def transform(self, X, y = None):
        # return the dataframe with the specified features
        X['name'] = X['brand'] + ' ' + X['model']
        X = X.drop(columns = ['brand', 'model'])
        return X
    
    
class Word2VecNameTransformer(BaseEstimator, TransformerMixin):
    def __init__(self, columns):
        self.columns = columns
        self.vector_size = 20
        self.window = 3
        self.min_count = 1
        self.workers = 1
        self.word2vec_model = None
        
    def fit(self, X, y = None):
        car_names = X['name']
        tokenized_names = [name.lower().split() for name in car_names]
        self.word2vec_model = Word2Vec(sentences=tokenized_names, vector_size=self.vector_size, window=self.window,
                                      min_count=self.min_count, workers=self.workers)
        return self
    
    def transform(self, X, y = None):
        car_names = X['name']
        print(X)
        tokenized_names = [name.lower().split() for name in car_names]
        
        vectorized_names = [self.word2vec_model.wv[name] for name in tokenized_names]
        vectorized_names = [vectorized_names[i].mean(axis = 0) for i in range(0, len(vectorized_names))]
        
        vector_df = pd.DataFrame(vectorized_names, columns=[f"Vector_{i+1}" for i in range(self.word2vec_model.vector_size)])
        
        vector_df.index = X.index 
        
        X = pd.concat([X, vector_df], axis=1)
        X = X.drop(columns = ['name'])
        
        return X
 
class StandardScalerTransformer(BaseEstimator, TransformerMixin):
    def __init__(self):
        self.scaler = StandardScaler()
        
    def fit(self, X, y = None):
        self.scaler.fit(X)
        return self
    
    def transform(self, X, y = None):
        X_scaled = pd.DataFrame(self.scaler.transform(X), columns=X.columns)
        return X_scaled
    
class KnnModel(BaseEstimator, TransformerMixin):
    def __init__(self, k):
        self.knn_model = NearestNeighbors(n_neighbors = k)
        
    def fit(self, X, y = None):
        self.knn_model.fit(X)
    
    def transform(self, X, y = None):
        _, indices = self.knn_model.kneighbors(X)
        return indices