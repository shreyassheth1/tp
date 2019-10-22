import sys
import os
import math

f = True
while(f):
	f = False
	ipIn = input("Enter ip address to assign : ")
	ipIn = ipIn.split("/")
	ip = ipIn[0].split(".")
	if(len(ip) != 4):
		print("invalid ip address")
		f = True
		continue

	ip = list(map(int, ip))

	for i in range(4):
		if(ip[i] < 0 or ip[i] > 255):
			print("invalid ip address")
			f = True

ipClass = ""
netID = 0
if ip[0] > 239:
	ipClass = 'E'
elif ip[0] > 223:
	ipClass = 'D'
elif ip[0] > 191:
	ipClass = 'C'
	netID = 24
elif ip[0] > 127:
	ipClass = 'B'
	netID = 16
else:
	ipClass = 'A'
	netID = 8

print("IP address belongs to class ", ipClass)


numBits = int(ipIn[1]) - netID

cnt = 0
ans = ""
if ipClass == 'A':
	ans = "255"
	print("Default Subnet Mask : 255.0.0.0")
	cnt = 3
elif ipClass == 'B':
	ans = "255.255"
	print("Default Subnet Mask : 255.255.0.0")
	cnt = 2
elif ipClass == 'C':
	ans = "255.255.255"
	print("Default Subnet Mask : 255.255.255.0")
	cnt = 1

octs = (numBits/8)
while octs >= 0:
	neti = 0
	i = 7
	while numBits > 0 and i>=0:
		neti = neti + (2**i)
		i = i-1
		numBits = numBits - 1
	ans = ans + "." + str(neti)
	octs = octs-1
	cnt = cnt-1
while(cnt > 0):
	ans = ans + ".0"
	cnt = cnt-1

print("Subnet mask : ", ans)

intrf = input("Enter interface : ")

os.system("sudo ifconfig " + intrf + " " + ipIn[0] + " netmask " + ans)

ipIn = ""
f = True
while(f):
	f = False
	ipIn = input("Enter IP address to ping : ")
	ip = ipIn.split(".")
	if(len(ip) != 4):
		print("invalid ip address")
		f = True

	ip = list(map(int, ip))

	for i in range(4):
		if(ip[i] < 0 or ip[i] > 255):
			print("invalid ip address")
			f = True
os.system("ping " + ipIn + " -c 5")