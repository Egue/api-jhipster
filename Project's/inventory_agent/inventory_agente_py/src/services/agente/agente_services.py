import platform
import psutil
import socket

def get_system_info():
    return {
        "hostname": platform.node(),
        "os":{
            "family" : platform.system(),
            "version": platform.version(),
            "arch" : platform.machine()
            
        },
        "procesador":{
            "name": platform.processor(),
            "identifier":platform.uname().processor,
            "vendor":platform.uname().node,
            "frecuency":psutil.cpu_freq().max if psutil.cpu_freq() else 0,
            "cores": psutil.cpu_count(logical=False)
        },
        "memory":{
            "total":psutil.virtual_memory().total,
            "available":psutil.virtual_memory().available,
            "used":psutil.virtual_memory().used
        },
        "disk":{
            "total":psutil.disk_usage("/").total,
            "available":psutil.disk_usage("/").free,
            "used":psutil.disk_usage("/").used
        },
        "network":[
            {
                "macAddress":addr.address,
                "ipAddress":addr.address if addr.family == socket.AF_INET else "N/A"
                
            }
            for interface , addrs in psutil.net_if_addrs().items()
            for addr in addrs if addr.family in (socket.AF_INET , psutil.AF_LINK)
        ],
        "manufacturer": {
            "name": "uknow",
            "model":"unknow",
            "serialNumber":"unknow",
            "chassisType":"unknow"
        }
    }

    