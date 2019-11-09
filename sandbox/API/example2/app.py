from flask import Flask
from flask_restful import Resource, Api, request, reqparse
from flask_jwt import JWT, jwt_required
from security import authenticate, identity

items = []                      # in memory database 
app = Flask(__name__)

app.secret_key = "password"     # private key

api = Api(app)
jwt = JWT(app, authenticate, identity)   # /auth

class Item(Resource):
    # method signature of get/post must be the same

    @jwt_required()
    def get(self, name):
        for item in items:
            if item['name'] == name:
                return item  # flask_restful convert to JSON
        
        return {'message' : 'error'}, 404
                
    def post(self, name):
        try:
            payload = request.get_json()

            items.append({
                'name'  : name,
                'price' : payload['price']
            })

            return {'message' : 'ok'}, 201

        except:
            return {'message' : 'error'}, 500

    def delete(self, name):
        for item in items:
            if item['name'] == name:
                items.remove(item)

                return {'message' : 'ok'}, 200
        
        return {'message' : 'error'}, 404

    def put(self, name):
        # validate JSON parameters
        parser = reqparse.RequestParser()
        parser.add_argument('price', type=float, required=True, help="This field cannot be empty")

        try:
            payload = parser.parse_args()

            for item in items:
                if item['name'] == name:
                    item['price'] = payload['price']

                    return {'message' : 'ok'}, 200
            
            items.append({
                'name'  : name,
                'price' : payload['price']
            })

            return {'message' : 'ok'}, 200

        except:
            return {'message' : 'error'}, 500


class ItemList(Resource):
    def get(self):
        return {'items' : items}, 200


api.add_resource(Item, '/item/<string:name>')
api.add_resource(ItemList, '/items')

app.run(debug=True)