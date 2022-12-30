from flask import Blueprint, redirect, url_for, request
from flask_login import login_user, login_required, logout_user
from werkzeug.security import check_password_hash

from backend.hotel.models.models import User
from backend.hotel.routes.helpers import *

auth = Blueprint('auth', __name__)


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
