import sqlite3
from flask_restful import Resource, reqparse
from models.user import UserModel


class UserRegister(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        
        parser.add_argument('username', type=str, required=True, help="This field cannot be empty")
        parser.add_argument('password', type=str, required=True, help="This field cannot be empty")
        
        payload = parser.parse_args()

        if UserModel.find_by_username(payload['username']):
            return {'message' : 'User already exits'}, 201

        else:
            user = UserModel(payload['username'], payload['password'])
            user.save()
            
            return {'message' : 'User created without issues'}, 201
