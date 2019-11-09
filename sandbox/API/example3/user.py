import sqlite3
from flask_restful import Resource, reqparse

class User:
    def __init__(self, _id, username, password):
        self.id = _id
        self.username = username
        self.password = password

    @classmethod
    def find_by_username(cls, username):
        connection = sqlite3.connect('data.db')
        cursor = connection.cursor()

        query = "SELECT * FROM users where username = ?"
        result = cursor.execute(query, (username,))
        row = result.fetchone()

        if row:
            user = cls(*row)

        else:
            user = None
        
        cursor.close()
        connection.close()

        return user

    @classmethod
    def find_by_id(cls, _id):
        connection = sqlite3.connect('data.db')
        cursor = connection.cursor()

        query = "SELECT * FROM users where id = ?"
        result = cursor.execute(query, (_id,))
        row = result.fetchone()

        if row:
            user = cls(*row)

        else:
            user = None
        
        cursor.close()
        connection.close()

        return user

class UserRegister(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        
        parser.add_argument('username', type=str, required=True, help="This field cannot be empty")
        parser.add_argument('password', type=str, required=True, help="This field cannot be empty")
        
        payload = parser.parse_args()

        if User.find_by_username(payload['username']):
            return {'message' : 'User already exits'}, 201

        else:
            connection = sqlite3.connect('data.db')
            cursor = connection.cursor()

            query = "INSERT INTO users VALUES (NULL, ?, ?)"
            cursor.execute(query, (payload['username'], payload['password']))

            connection.commit()
            connection.close()

            return {'message' : 'User created without issues'}, 201
