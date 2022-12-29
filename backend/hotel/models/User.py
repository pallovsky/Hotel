import uuid

from flask_authorize import RestrictionsMixin, AllowancesMixin
from flask_login import UserMixin
from sqlalchemy.dialects.postgresql import UUID

from backend.hotel import db

# mapping tables
UserGroup = db.Table(
    'user_group', db.Model.metadata,
    db.Column('user_id', UUID, db.ForeignKey('users.id')),
    db.Column('group_id', UUID, db.ForeignKey('groups.id'))
)


UserRole = db.Table(
    'user_role', db.Model.metadata,
    db.Column('user_id', UUID, db.ForeignKey('users.id')),
    db.Column('role_id', UUID, db.ForeignKey('roles.id'))
)


class User(UserMixin, db.Model):
    __tablename__ = 'users'

    id = db.Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    username = db.Column(db.String())
    email = db.Column(db.String())
    password = db.Column(db.String())
    roles = db.relationship('Role', secondary=UserRole)
    groups = db.relationship('Group', secondary=UserGroup)

    def __init__(self, username, email, password, role):
        self.id = uuid.uuid4()
        self.username = username
        self.email = email
        self.password = password
        self.role = role

    def __repr__(self):
        return f"<User {self.username}, email {self.email}, role {self.role}>"


class Group(db.Model, RestrictionsMixin):
    __tablename__ = 'groups'

    id = db.Column(UUID, primary_key=True, default=uuid.uuid4)
    name = db.Column(db.String(255), nullable=False, unique=True)


class Role(db.Model, AllowancesMixin):
    __tablename__ = 'roles'

    id = db.Column(UUID, primary_key=True, default=uuid.uuid4)
    name = db.Column(db.String(255), nullable=False, unique=True)
