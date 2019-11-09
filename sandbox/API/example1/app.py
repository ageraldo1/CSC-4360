from flask import Flask, jsonify, request, render_template

app = Flask(__name__)

stores = [
    {
        'name': 'Store 1',
        'items' : [
            {
                'name' : 'Item 1',
                'price' : 15.99
            }
        ]
    }
]

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/store', methods=['POST'])
def create_store():
    request_data = request.get_json()
    stores.append({
        'name'  : request_data['name'],
        'items' : [] 
    })

    return jsonify({'result': 'ok'})

@app.route('/store/<string:name>', methods=['GET'])
def get_store(name):
    for store in stores:
        if store['name'] == name:
            return jsonify(store)
    
    return jsonify({'result': 'error'})
        

@app.route('/store', methods=['GET'])
def get_stores():
    return jsonify({'stores' : stores}) # always starts with a dictionary


@app.route('/store/<string:name>/item', methods=['POST'])
def create_item_in_store(name):
    request_data = request.get_json()

    for store in stores:
        if store['name'] == name:
            store['items'].append({
                'name'  : request_data['name'],
                'price' : request_data['price']
            })
            return jsonify({'result': 'ok'})
    
    return jsonify({'result': 'error'})

@app.route('/store/<string:name>/item', methods=['GET'])
def get_item_in_store(name):
    for store in stores:
        if store['name'] == name:
            return jsonify({'items' : store['items']})
    
    return jsonify({'result': 'error'})

app.run(port=5000, debug=True)
