#serial = 6548
serial=6548
def makeGrid(serial):
    board={}
    for x in range(1,301):
        for y in range(1,301):
            rackid = x + 10
            tmp = rackid * y + serial
            tmp = tmp * rackid
            tmp = tmp // 100
            tmp = tmp % 10
            tmp = tmp - 5
            board[(x,y)] = tmp
    return board



def calcPower(grid,x,y):
    deltas=[(-1,-1),(0,-1),(1,-1),
            (-1,0),(0,0),(1,0),
            (-1,1),(0,1),(1,1)]
    sum = 0
    for (dx,dy) in deltas:
        try:
            sum = sum + grid[( (x+dx),(y+dy) )]  
        except:
            pass
    return sum

def calcAllPowers(grid):
    p={}
    for x in range(1,301):
        for y in range(1,301):
            s = calcPower(grid,x,y)
            p[(x,y)] = s
    return p
def calcPower2(grid,x,y,s):
    deltas=[ (x,y) for x in range(s) for y in range(s)]
    sum = 0
    for (dx,dy) in deltas:
        try:
            sum = sum + grid[( (x+dx),(y+dy) )]  
        except:
            pass
    return sum
def calcAllPowers2(grid):
    mv = 0
    
    for size in range(2,20):
        p={}

        for x in range(1,301):
            for y in range(1,301):
                s = calcPower2(grid,x,y,size)
                p[(x,y)] = s
        v = max(p.values())
        if v > mv:
            s = size
            mv = v
            pt = [k for k in p if p[k]==v]
            print(mv,pt,s)

    return (mv,pt,s)

def calc_new(baseboard,b,s,x,y):
    deltas=[ (x,y) for x in range(s) for y in range(s)]
    deltas = [(x,s-1) for x in range(s) ]
    deltas = deltas +  [(s-1,y) for y in range(s) ]
    s = b[(x,y)]
    for (dx,dy) in deltas:
        try:
            s = s + baseboard[( (x+dx),(y+dy) )]  
        except:
            pass
    return s

# part 1
#g = makeGrid(serial)
#p = calcAllPowers(g)
#v = max(p.values())
#[k for k in p if p[k]==v]


#part 2
def part2():
    baseboard = makeGrid(serial)
    b = makeGrid(serial)
    for (x,y) in b.keys():
        b[(x,y)] = (b[(x,y)],1)
    for s in range(2,301):
        
        for x in range(1,301-s):
            for y in range(1,301-s):
                np = calc_new(baseboard,b,s,x,y)
                if b[(x,y)][0] < np:
                    b[(x,y)] = (np,s)
    print(max(b.values()))            
    return b

part2()
