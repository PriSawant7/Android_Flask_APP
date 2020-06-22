from flask import Flask, request
import json
from tinydb import TinyDB,Query


app = Flask(__name__)
db = TinyDB('db.json')
Record = Query()

@app.route('/', methods=['GET'])
def get_app_versions():
    print(f'Request: {request}')
    return json.dumps({"items": db.all()})

@app.route('/version', methods=['GET'])
def update_to_latest_version():
    current_version = request.args.get('current_version')
    print('Current version=',current_version)
    last_row = db.get(doc_id=len(db))
    print(last_row)
    return last_row

@app.route('/newVersions',methods=['GET'])
def get_new_versions():
    current_version = request.args.get('current_version')
    print('Current version=',current_version)
    print('Get all versions newer than current version')
    newer_versions = db.search(Record.version >= current_version)
    print('Newer versions==')
    print(newer_versions)
    return {'newer_versions':newer_versions}

if __name__ == '__main__':
    app.run()
