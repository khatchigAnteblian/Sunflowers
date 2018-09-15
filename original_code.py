def find_rotation(cnrs):
    if min(cnrs) == cnrs[0]:
        return 0
    elif min(cnrs) == cnrs[1]:
        return 90 
    elif min(cnrs) == cnrs[2]:
        return 180
    elif min(cnrs) == cnrs[3]:
        return 270

def rotate_left(data):
    result = []
    for i in range(numofflowers+1, 2*numofflowers+1):
        currentRow = [num for row in data for num in row if num == row[numofflowers - i]]
        result.append(currentRow)
    return result

def rotate_right(data):
    result = []
    for i in range(numofflowers, 0, -1):
        currentRow = [num for row in data for num in row if num == row[numofflowers - i]]
        result.append(currentRow[::-1])
    return result

def rotate(data, direction, repetition):
    if direction == "r":
        if repetition == 1:
            return rotate_right(data)
        elif repetition == 2:
            return rotate_right(rotate_right(data))

    elif direction == "l":
        if repetition == 1:
            return rotate_left(data)
        elif repetition == 2:
            return rotate_left(rotate_left(data))


# In the original contest, the data was given through stdin, so I'm reading
# stdin instead of from a file.
numofflowers = int(input())
rows = [[int(i) for i in input().split()] for i in range(numofflowers)]
corners = [rows[0][0], rows[0][-1], rows[-1][-1], rows[-1][0]]
rot = find_rotation(corners)

if rot == 0:
for i in range(numofflowers):
    print(' '.join(str(n) for n in rows[i]))
elif rot == 90:
    for i in range(numofflowers):
        print(' '.join(str(n) for n in rotate(rows, 'l', 1)[i]))
elif rot == 180:
    for i in range(numofflowers):
        print(' '.join(str(n) for n in rotate(rows, 'l', 2)[i]))
elif rot == 270:
    for i in range(numofflowers):
        print(' '.join(str(n) for n in rotate(rows, 'r', 1)[i]))

