import sys

sys.setrecursionlimit(20000)
# ans is 138
#line = open("day08.txt").read()
#tree = [int(x) for x in line.strip().split()]
#tree = [2,3,0,3,10,11,12,1,1, 0, 1, 99, 2, 1, 1, 2]
#tree = [x for x in reversed(tree) ]
s=0
def solve1():
    global s
    if len(tree)<2:
        return 0
    c=tree.pop()
    metas = tree.pop()
    for i in range(c):
        solve1()
    for j in range(metas):
        s = s + tree.pop()
        
    

line = open("day08.txt").read()
tree = [int(x) for x in line.strip().split()]
#tree = [2,3,0,3,10,11,12,1,1, 0, 1, 99, 2, 1, 1, 2]
tree = [x for x in reversed(tree) ]
result=[]
nodes={}
def solve2():
    global result
    
    
    c = tree.pop()
    metas = tree.pop()
    tmp = [0 for q in range(max(metas,c)+10)]
    for i in range(c):
        tmp[i]=solve2()
    
    r=0
    if c==0:
        for j in range(metas):
            r = r+tree.pop()
    else:
        for j in range(metas):
            x = tree.pop()
            
            r = r + tmp[x-1]
            
    
    
    return r;    

line2 = open("day08.txt").read()
tree2 = [int(x) for x in line2.strip().split()]
final_metas = [ 1, 10, 8, 8, 8, 5, 3, 1, 6, 10, 6]

#part 1: 42254
#part 2: 25007
