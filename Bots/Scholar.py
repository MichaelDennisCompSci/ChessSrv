import getpass
import sys
import telnetlib
import random

HOST = sys.argv[1]
tn = telnetlib.Telnet(HOST,5490)
done = False
scholars_mate = ["Qxf7","Qh5","Bc4","e4"]
while (not done):
    match = tn.expect(["You are Black","Enter a valid move:","WHITE wins","BLACK wins","draw"])
    if match[1].group(0)=="You are Black":
        scholars_mate = ["Qxf2","Qh4","Bc5","e5"]
    elif match[1].group(0)=="Enter a valid move:":
        data = match[2]
        print data
        moves = data.split("[")[1].split("]")[0].split(", ")
        index = random.randint(0,len(moves)-1)
        for move in scholars_mate:
            if move in moves:
                index = moves.index(move)
        print moves[index]
        tn.write(moves[index]+"\n")
    else:
        done = True
        print match[2]
