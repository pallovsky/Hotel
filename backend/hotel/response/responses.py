class GameResponse:
    def __init__(self, _id, name, user_count):
        self.id = _id
        self.name = name
        self.user_count = user_count

    def serialize(self):
        return {"id": self.id,
                "name": self.name,
                "userCount": self.user_count
                }
