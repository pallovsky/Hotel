from flask import Flask
from flask_authorize import Authorize
from flask_login import LoginManager
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS


db = SQLAlchemy()
migrate = Migrate()
login_manager = LoginManager()
authorize = Authorize()


def init_app():
    app = Flask(__name__)

    setup_config(app)
    initialize_plugins(app, db)
    register_blueprints(app)

    return app


def setup_config(application):
    application.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://postgres:postgres@localhost:5432/hotel"
    application.config['SECRET_KEY'] = 'jebac-policje'


def initialize_plugins(app, database):
    database.init_app(app)
    migrate.init_app(app, database)
    from backend.hotel.models.User import User, Game  # add model to initiate migration with flask migrate

    login_manager.init_app(app)
    authorize.init_app(app)
    CORS(app)

    @login_manager.user_loader
    def load_user(user_id):
        return User.query.get(user_id)


def register_blueprints(app):
    from backend.hotel.routes.auth import auth as auth_blueprint
    app.register_blueprint(auth_blueprint)

    from backend.hotel.routes.main import main as main_blueprint
    app.register_blueprint(main_blueprint)

    from backend.hotel.routes.games import games as games_blueprint
    app.register_blueprint(games_blueprint)
