from flask_jwt import jwt_required
from flask_restful import Resource, request, reqparse
from models.item import ItemModel

class Item(Resource):
    # method signature of get/post must be the same

    @jwt_required()
    def get(self, name): 
        item = ItemModel.find_by_name(name)

        if item:
            return item.json()

        return { 'message' : f'Item {name} not found'}
 
    def post(self, name):

        if ItemModel.find_by_name(name):
            return { 'message' : f'Item {name} already exists'}

        try:
            payload = request.get_json()

            item = ItemModel(name, payload['price'], payload['store_id'])
            item.save()

            return {'message' : 'ok'}, 201

        except:
            return {'message' : 'error'}, 500

    def delete(self, name):
        item = ItemModel.find_by_name(name)

        if item:            
            item.delete()
            return {'message' : 'ok'}, 201

        else:
            return {'message' : f'Item {name} not found'}, 404

    def put(self, name):
        # validate JSON parameters
        parser = reqparse.RequestParser()
        parser.add_argument('price', type=float, required=True, help="This field cannot be empty")
        parser.add_argument('store_id', type=int, required=True, help="This field cannot be empty")

        try:
            payload = parser.parse_args()

            item = ItemModel.find_by_name(name)

            if item:
                item.price = payload['price']

            else:
                item = ItemModel(name, payload['price'],  payload['store_id'] )
            
            item.save()

            return {'message' : 'ok'}, 200

        except:
            return {'message' : 'error'}, 500


class ItemList(Resource):
    def get(self):
        return {'items' : [item.json() for item in ItemModel.query.all()]}, 200
