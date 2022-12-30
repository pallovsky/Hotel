from flask import Blueprint, jsonify, request
from werkzeug.security import generate_password_hash

from backend.hotel import authorize, db
from backend.hotel.models.models import User, Role
from backend.hotel.response.responses import UserResponse
from backend.hotel.routes.helpers import created
from backend.hotel.routes.validators import validate_register_request

users = Blueprint('users', __name__)


@users.route('/users')
@authorize.has_role('admin')
def get_users():
    user_models = User.query.all()
    user_responses = [UserResponse(user.id, user.username, user.email, user.roles[0].name, len(user.games)).serialize()
                      for user in user_models]

    return jsonify(user_responses)


@users.route('/users', methods=['POST'])
@authorize.has_role('admin')
def register():
    email = request.form.get('email')
    username = request.form.get('username')
    password = request.form.get('password')

    validation = validate_register_request(email, username, password)
    if not validation.success:
        return validation.error_response

    role = Role.query.filter_by(name='user').first()
    password_hash = generate_password_hash(password, method='sha256')

    new_user = User(email=email, username=username, roles=[role], password=password_hash)

    db.session.add(new_user)
    db.session.commit()

    return created("User was successfully created.")
