from flask import Flask
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy
from routes.auth import auth as auth_blueprint
from routes.main import main as main_blueprint

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://postgres:postgres@localhost:5432/hotel"
app.register_blueprint(auth_blueprint)
app.register_blueprint(main_blueprint)

db = SQLAlchemy(app)
migrate = Migrate(app, db)

# This import is needed to be here for models be detected by flask migrate.
from models import *

if __name__ == "__main__":
    app.run(debug=True)
