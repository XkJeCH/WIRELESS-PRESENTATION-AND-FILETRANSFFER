//Get data
const { initializeApp } = require("firebase/app");
const { getFirestore,collection,getDocs} = require("firebase/firestore");


const firebaseConfig = {
  apiKey: "AIzaSyCDhXc5YbUEUoMtr-ThagV-jr8xzv6lxS8",
  authDomain: "cloudwireless-9f89a.firebaseapp.com",
  databaseURL: "https://cloudwireless-9f89a-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "cloudwireless-9f89a",
  storageBucket: "cloudwireless-9f89a.appspot.com",
  messagingSenderId: "1004514098764",
  appId: "1:1004514098764:web:afc7d3705a1e9f7aa6fee9",
  measurementId: "G-1F52Z76G4J"
};


initializeApp(firebaseConfig)

const database = getFirestore();

const docRef = collection(database,'images');

getDocs(docRef)
  .then((snapshot) => {
    let items = []
    snapshot.docs.forEach((doc) => {
      items.push({...doc.data(),id:doc.id})
    })
    console.log(items)
  })
  .catch(err =>{
    console.log(err.message)
  })


