from flask import Flask
from flask_restplus import Resource, Api, Namespace
from flask_cors import CORS 
from flask_jwt import JWT

from security import authenticate, identity
from resources.user import User
from resources.user import UserList

from resources.pet import PetAPI
from resources.pet import Pet
from resources.pet import PetList

from resources.doctor import DoctorAPI
from resources.doctor import Doctor
from resources.doctor import DoctorList

app = Flask(__name__)
app.config.from_object('config')

CORS(app)
api = Api(app, version='1.0', title='PetMan App API',  description='Flask API PetMan Application - GSU 2019')

@app.before_first_request
def create_tables():
    db.create_all()

jwt = JWT(app, authenticate, identity)

api.add_resource(User, '/register')
api.add_resource(User, '/user/<string:username>')
api.add_resource(UserList, '/users/')

api.add_resource(PetAPI, '/pet')
api.add_resource(Pet, '/pet/<string:name>/<string:owner>')
api.add_resource(PetList, '/pets/<string:owner>')

api.add_resource(DoctorAPI, '/doctor')
api.add_resource(Doctor, '/doctor/<string:_id>/<string:pet_id>')
api.add_resource(DoctorList, '/doctors/<string:pet_id>')

if __name__ == '__main__':
    from db import db

    db.init_app(app)
    app.run(debug=True)
