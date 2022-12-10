from flask import Flask
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()
migrate = Migrate()


def init_app():
    app = Flask(__name__)
    app.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://postgres:postgres@localhost:5432/hotel"

    initialize_plugins(app, db)
    register_blueprints(app)

    return app


def initialize_plugins(app, database):
    database.init_app(app)
    migrate.init_app(app, database)
    from backend.hotel.models.user import User  # line used to initiate migrations


def register_blueprints(app):
    from backend.hotel.routes.auth import auth as auth_blueprint
    app.register_blueprint(auth_blueprint)

    from backend.hotel.routes.main import main as main_blueprint
    app.register_blueprint(main_blueprint)
