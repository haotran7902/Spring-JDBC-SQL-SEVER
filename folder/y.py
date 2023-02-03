import re
regex = '[\d.,-]+'
file = open("DATA.txt", 'r', encoding = 'utf-8')
lst, tmp = [], []
share = []
data = file.read()
for i in range(len(data)):
    if data[i] == '>' and data[i+1] == '<':
        data = data[:i] + "00" + data[(i+1):] 
    if data[i] == '<' and data[i+1] == 't' and data[i+2] == 'r' and i < len(data)-4:
        share.append(data[(i+8):(i+11)])
        data = data[:i] + " 99999" + data[(i+1):]

result = []
lst.extend(re.findall(regex, data))
for i in lst:
    if len(i) > 1: tmp.append(i)
lst = tmp[1:]
temp = []
for i in range(len(lst)):
    if lst[i] == '99999': 
        if temp[0] != '00': temp = temp[1:]
        res = temp[2:6] + temp[7:16]
        if temp[15] == '0': res = res + temp[16:25]
        else: res = res + temp[17:26]
        result.append(res)
        temp = []
    else: 
        temp.append(lst[i])
for i in range(len(result)):
    print(share[i] + ' ' + ' '.join(result[i]))