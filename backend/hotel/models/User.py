import uuid

from flask_login import UserMixin
from sqlalchemy.dialects.postgresql import UUID

from backend.hotel import db


class User(UserMixin, db.Model):
    __tablename__ = 'Users'

    id = db.Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    username = db.Column(db.String())
    email = db.Column(db.String())
    password = db.Column(db.String())
    role = db.Column(db.Integer())

    def __init__(self, username, email, password, role):
        self.id = uuid.uuid4()
        self.username = username
        self.email = email
        self.password = password
        self.role = role

    def __repr__(self):
        return f"<User {self.username}, email {self.email}, role {self.role}>"
