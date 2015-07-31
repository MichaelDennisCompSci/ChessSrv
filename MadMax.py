import getpass
import sys
import telnetlib
import random

HOST = sys.argv[1]
tn = telnetlib.Telnet(HOST,5490)
while (True):
    data = tn.read_until("Enter a valid move:")
    print data
    moves = data.split("[")[1].split("]")[0].split(", ")
    index = random.randint(0,len(moves)-1)
    tn.write(moves[index]+"\n")
