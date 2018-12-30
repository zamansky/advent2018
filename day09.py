num_players =431 # 9
last_score = 70950 # 255
players = [0 for x in range(num_players+1)]

player=3
board=[0,2,1]
next_marble = 3
index=1
while next_marble < last_score:
    if next_marble % 23 != 0:
        #regular insert
        L = len(board)
        index=(index+1)%L
        index=(index+1)%L
        board.insert(index,next_marble)
    else:
        players[player]=players[player]+ next_marble
        index=(index-7)%len(board)
        players[player]=players[player]+board[index]
        del board[index]
    player=(player+1)%num_players
    next_marble = next_marble + 1
print(max(players))
