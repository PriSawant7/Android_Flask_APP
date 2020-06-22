import re

s="[abc][zxy][e]"

sl=re.split('\]\[',s)

print(sl)
res=[]
# for regex in sl:
#     temp=[]
#     for i in regex:
#         o=ord(i)
#         if o>=97 and o<=122:
#             temp.append(o)
#     res.append(chr(min(temp)))

# print('res=',''.join(res))


for regex in sl:
    print((regex))
    regex=regex.replace('[','')
    regex=regex.replace(']','')

    res.append(min(regex))
    print(regex)
print(res)




