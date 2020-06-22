from firebase import firebase

firebase = firebase.FirebaseApplication('https://android-app-auto-update.firebaseio.com', None)
data={"versions": {
    "1": {"version": "1.0", "location": ""}, 
    "2": {"version": "2.0", "location": ""},
    "3": {"version": "3.0", "location": ""},
    "4": {"version": "4.0", "location": ""},
    "5": {"version": "5.0", "location": ""}
}}

# result = firebase.post('/',data)
result = firebase.get('android-app-auto-update/MAT12ZmoYhbwFpQDKhq/versions','1')
print(result)








