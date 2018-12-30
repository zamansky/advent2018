import re
import sys
forward = {}
back = {}
allnodes = set()
for line in open("day07.txt").readlines():
    r = re.search(".* ([A-Z]) .* ([A-Z]) ",line)
    (s,d) = r.groups()
    forward.setdefault(s,[])
    back.setdefault(d,[])
    forward[s].append(d)
    back[d].append(s)
    allnodes.update(s)
    allnodes.update(d)
print(back)
def part1():
    candidates=[]
    for n in allnodes:
        if n not in back.keys():
            candidates.append(n)
    candidates = sorted(candidates)
    candidates = candidates[::-1]
    print(candidates)
    solset=[]
    while set(solset) != allnodes:
        next = candidates.pop()
        solset.append(next)
        # remove next from all prepreqs (backs)
        for k in back:
            if next in back[k]:
                back[k].remove(next)
        # find all nodes with no prereq and add to candidates
        removes=[]
        for k in back:
            #print(k,back[k],len(back[k]))
            if len(back[k])==0:
                candidates.append(k)
                removes.append(k)
                # remove those nodes from back
        for n in removes:
            back.pop(n)
        # sort the candidates
        candidates =sorted(candidates)
        candidates = candidates[::-1]
    part1sol="".join(solset)
    return part1sol


def part2():
    workers=5
    basetime=60
    working_workers=0
    
    candidates=[]
    for n in allnodes:
        if n not in back.keys():
            candidates.append(n)
    candidates = sorted(candidates)
    candidates = candidates[::-1]
    print(candidates)
    workqueue=[]
    solset=[]

    t=0
    while set(solset) != allnodes:
        while len(candidates) > 0 and working_workers < workers:
            next = candidates.pop()
            nextime = t + basetime+ord(next)-65+1
            print(next,nextime)
            workqueue.append((nextime,next))
            working_workers = working_workers + 1

        workqueue.sort(key=lambda tup: tup[0])
        workqueue = workqueue[::-1]
        print(workqueue)

        next = workqueue.pop()
        working_workers=working_workers-1            
        t=next[0]
        solset.append(next[1])
        # remove next from all prepreqs (backs)
        next=next[1]
        for k in back:
            if next in back[k]:
                back[k].remove(next)
        # find all nodes with no prereq and add to candidates
        removes=[]
        for k in back:
            #print(k,back[k],len(back[k]))
            if len(back[k])==0:
                candidates.append(k)
                removes.append(k)
                # remove those nodes from back
        
        for n in removes:
            back.pop(n)
        # sort the candidates
        candidates =sorted(candidates)
        candidates = candidates[::-1]
        print(candidates)
    part1sol="".join(solset)
    print(t)
    return part1sol



    
