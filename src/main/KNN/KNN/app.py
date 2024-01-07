from flask import Flask, request, jsonify
import pandas as pd
from joblib import load
from flask_cors import CORS

from sklearn.base import BaseEstimator, TransformerMixin
# (BaseEstimator, TransformerMixin). This makes it compatible with 
# scikit-learn’s Pipelinesclass ColumnsSelector(BaseEstimator, TransformerMixin):
    # initializer 
from transformers import RemoveColumnsTransformer, \
                            OHETransformer, \
                            BinaryTransformer, \
                            StringToIntTransformer, \
                            LowerCaseTransformer, \
                            Word2VecNameTransformer, \
                            StandardScalerTransformer, \
                            KnnModel

app = Flask(__name__)
CORS(app)

# Load the pre-trained k-NN pipeline
knn_pipeline = load('knn_pipeline.joblib')

df = pd.read_csv('./output_WITHOUT_DUPLICATES.csv')

@app.route('/predict', methods=['POST'])
def predict():
    datas = request.json['data']
    
    df_liked = df[df['ad_id'].isin(datas)]
    print(df_liked)

    indices = []
    for index, row in df_liked.iterrows():
        row_data = pd.DataFrame(row).transpose()
        indices_crt = knn_pipeline.transform(row_data)
        indices_crt = indices_crt.tolist()
        indices_crt = indices_crt[0]
        indices.extend(indices_crt)

    df_neighbors = df.iloc[indices]

    neigbhros_ad_ids = df_neighbors['ad_id'].tolist()

    print(df_neighbors)

    return jsonify(neigbhros_ad_ids), 200

if __name__ == '__main__':
    app.run(debug=True)
