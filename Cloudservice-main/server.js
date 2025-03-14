const express = require('express');
const socketio = require('socket.io');
const app = express();
const os = require('os');

app.set("view engine","ejs");
app.use(express.static("public"));

app.get('/',(req,res)=>{
    const serverIp = getServerIpAddress()
    res.render('dowload',{serverIp});
})

app.get('/ipaddress',(req,res)=>{
    const serverIp = getServerIpAddress()
    res.json({serverIp})
})

const server = app.listen(process.env.PORT || 3000,() => {
    const ip = getServerIpAddress()
    const serverUrl = `http://${ip}:3000/`
    console.log("Server is running on "+ip)
    console.log(`Web browser running on link: \x1b]8;;${serverUrl}\x1b\\${serverUrl}\x1b]8;;\x1b\\`);
})  

const io = socketio(server)

let isButtonClicked = false;
let isFileClick = false;
let isViewClick = false

io.on('connection', socket => {
    console.log('A user connected');
    socket.on('scroll', data => {
        socket.broadcast.emit('scroll', data);
    });
    
    socket.emit('click', { clicked: isButtonClicked });
    socket.on('click', data => {
        isButtonClicked = data.clicked;

        io.emit('click', { clicked: isButtonClicked });
    });
    socket.on('image',data => {
        
    });
    socket.emit('fileButtonClick', { clicked: isFileClick });
    socket.on('file',data => {
        
    });
    socket.on('fileButtonClick', data=>{
        isFileClick = data.clicked

        io.emit('fileButtonClick',{clicked:isFileClick});
    });
    socket.on('pdf',data=>{
        console.log('click from client')
        isViewClick = data.clicked; // Get clicked state from client
        const url = data.url; // Get URL from client
        console.log(url)
        io.emit('pdf', { clicked: isViewClick, url: url });
    })
});

app.get('/ip',(req,res)=>{
    const serverIp = getServerIpAddress();
    
    res.send({ ip: serverIp });
})

const getServerIpAddress = function() {
    const interfaces = os.networkInterfaces();

    const wifiInterface = interfaces['Wi-Fi'];
    if (wifiInterface) {
        const wifiIpv4Address = wifiInterface.find(info => info.family === 'IPv4');
        if (wifiIpv4Address) {
            return wifiIpv4Address.address;
        }
    }

    const ethernet3Interface = interfaces['Ethernet 3'];
    if (ethernet3Interface) {
        const ethernet3Ipv4Address = ethernet3Interface.find(info => info.family === 'IPv4');
        if (ethernet3Ipv4Address) {
            return ethernet3Ipv4Address.address;
        }
    }

    const ethernetInterface = interfaces['Ethernet'];
    if (ethernetInterface) {
        const ethernetIpv4Address = ethernetInterface.find(info => info.family === 'IPv4');
        if (ethernetIpv4Address) {
            return ethernetIpv4Address.address;
        }
    }

    for (const interfaceName in interfaces) {
        if (interfaceName !== 'Wi-Fi' && interfaceName !== 'Ethernet 3' && interfaceName !== 'Ethernet' && interfaceName !== 'Radmin VPN') {
            const interfaceInfo = interfaces[interfaceName];
            const ipv4AddressInfo = interfaceInfo.find(info => info.family === 'IPv4');
            if (ipv4AddressInfo) {
                return ipv4AddressInfo.address;
            }
        }
    }

    return null; // Return null if no IPv4 address is found for any interface
}

module.exports = { getServerIpAddress };