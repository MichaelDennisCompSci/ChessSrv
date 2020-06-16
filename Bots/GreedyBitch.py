import getpass
import sys
import telnetlib
import random

HOST = sys.argv[1]
tn = telnetlib.Telnet(HOST,5490)
done = False
while (not done):
    match = tn.expect(["Enter a valid move:","WHITE wins","BLACK wins","draw"])
    if match[1].group(0)=="Enter a valid move:":
        data = match[2]
        print data
        moves = data.split("[")[1].split("]")[0].split(", ")
        index = random.randint(0,len(moves)-1)
        for (i,move) in enumerate(moves):
            if "x" in move:
                index = i
        print moves[index]
        tn.write(moves[index]+"\n")
    else:
        done = True
        print match[2]
