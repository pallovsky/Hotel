from flask import Blueprint, redirect, url_for, request
from flask_login import login_user, login_required, logout_user
from werkzeug.security import generate_password_hash, check_password_hash

from backend.hotel import db
from backend.hotel.models.User import User
from backend.hotel.routes.helpers import *
from backend.hotel.routes.validators import validate_register_request

auth = Blueprint('auth', __name__)


@auth.route('/register', methods=['POST'])
def register():
    email = request.form.get('email')
    username = request.form.get('username')
    password = request.form.get('password')

    validation = validate_register_request(email, username, password)
    if not validation.result:
        return validation.error_response

    user = User.query.filter_by(email=email).first()
    if user:
        return bad_request("User with this email already exist in database.")

    new_user = User(email=email, username=username, role=3, password=generate_password_hash(password, method='sha256'))

    db.session.add(new_user)
    db.session.commit()

    return created("User was successfully created.")


@auth.route('/login', methods=['POST'])
def login():
    username = request.form.get('username')
    password = request.form.get('password')
    remember = True if request.form.get('remember') else False

    user = User.query.filter_by(username=username).first()

    if not user or not check_password_hash(user.password, password):
        return unauthorized('Please check your login details and try again.')

    login_user(user, remember=remember)

    return ok("User logged in.")


@auth.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('main.index'))
