import sys
import os
import math

f = True
while(f):
	f = False
	ipIn = input("Enter ip address to assign : ")
	ip = ipIn.split(".")
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
if ip[0] > 239:
	ipClass = 'E'
elif ip[0] > 223:
	ipClass = 'D'
elif ip[0] > 191:
	ipClass = 'C'
elif ip[0] > 127:
	ipClass = 'B'
else:
	ipClass = 'A'

print("IP address belongs to class ", ipClass)

f = True
while(f):
	f = False
	numSubnet = int(input("Enter number of subnets : "))
	if ipClass == 'A' and (numSubnet < 2 or numSubnet > 16777216):
		print("Enter valid number of subnets")
		f = True
	elif ipClass == 'B' and (numSubnet < 2 or numSubnet > 65536):
		print("Enter valid number of subnets")
		f = True
	elif ipClass == 'C' and (numSubnet < 2 or numSubnet > 256):
		print("Enter valid number of subnets")
		f = True

numBits = math.ceil(math.log2(numSubnet))

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

os.system("sudo ifconfig " + intrf + " " + ipIn + " netmask " + ans)

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