from flask_jwt import jwt_required
from flask_restful import Resource, request, reqparse
from models.store import StoreModel

class Store(Resource):
    def get(self, name):
        store = StoreModel.find_by_name(name)

        if store:
            return store.json()

        else:
            return { 'message' : 'Store not found'}, 404

    def post(self, name):
        if StoreModel.find_by_name(name):
            return {'message' : f'A store with name {name} already exists'}, 400
        else:
            store = StoreModel(name)

            try:                
                store.save()

                return store.json(), 201
            except:
                return {'message' : 'An error occurred while creating the store.'}, 500

    def delete(self, name):
        store = StoreModel.find_by_name(name)

        if store:
            store.delete()

        return {'message' : 'Store deleted'}

class StoreList(Resource):
    def get(self):
        return { 'stores' : [store.json() for store in StoreModel.query.all()]}, 200
