from itertools import combinations
import pandas as pd
import csv
from collections import Counter
import time

def make_candidate(freq_itemsets, k) :
    candidates = set()
    for itemset1 in freq_itemsets :
        for itemset2 in freq_itemsets :
            union = itemset1 | itemset2
            if len(union) == k :
                for item in union :
                    if union - {item} not in freq_itemsets:
                        break
                else :
                    candidates.add(union)
    return candidates

def filter (candidates, k, s) :
    itemsets_cnt_k = {}
    # count fair
    with open("groceries.csv", "r") as f :
        for line in f :
            basket = line.strip().split(",")
            for comb in combinations(basket, k) :
                comb = frozenset(comb)
                if comb in candidates :
                    if comb not in itemsets_cnt_k :
                        itemsets_cnt_k[comb] = 0
                    itemsets_cnt_k[comb] += 1

    #print(candidates)
    freq_itemsets = set(itemset for itemset, cnt in itemsets_cnt_k.items() if cnt >= s)
    return freq_itemsets

apriori_start = time.time()

# count item
item_cnt = {}
s = 100
with open("groceries.csv", "r") as f :
    for line in f :
        basket = line.strip().split(",")
        for item in basket :
            if item not in item_cnt :
                item_cnt[item] = 0
            item_cnt[item] += 1

freq_itemsets = set() # L1
# freq_items = set(item for item,cnt in item_cnt.items() if cnt >= s)
for item, cnt in item_cnt.items() :
    if cnt >= s :
        freq_itemsets.add(frozenset([item]))

freq_itemsets_all = freq_itemsets.copy()
k = 2

#freq_itemsets = set(frozenset([item]) for item,cnt in item_cnt.items() if cnt >= s)
while len(freq_itemsets) > 0 :
    candidates = make_candidate(freq_itemsets, k)
    freq_itemsets = filter(candidates,k,s)
    freq_itemsets_all |= freq_itemsets
    #print(k, len(candidates), len(freq_itemsets))
    k+=1
# for fi in freq_itemsets_all :
#     print(fi)

### association rule
# conf = support(I U {j})/support(I)

def support(I, j) :
    cnt1 = 0 # support union I | j
    cnt2 = 0 # support I
    with open("groceries.csv", "r") as f :
        for line in f :
            basket = line.strip().split(",")
            basket = frozenset(basket)
            #print(basket)
            if len(I | j | basket) == len(basket) :
                cnt1 += 1
                cnt2 += 1
            elif len(I|basket) == len(basket) :
                cnt2 += 1
    return cnt1, cnt2

def conf(I, j) :
    cnt1, cnt2 = support(I, j)
    if cnt2 == 0 : return 0
    ret = cnt1/cnt2
    return ret


# test
test_list = []
for target in freq_itemsets_all :
    cnt1 = 0
    cnt2 = 0
    for i in range(1,len(target)) :
        for j in combinations(target, i) :
            j = frozenset(j)
            I = target - j
            #print(I)
            c = conf(I, j)
            # c값 설정
            if c >= 0.5 :
                test_list.append([I,j,round(c,2)])
                print(I, j, round(c,2))    
apriori_end = time.time()

print(f"apriori run time: {apriori_end - apriori_start:.6f} sec")



"""
    검토용 test (메모리는 고려안함)
    실제 데이터 셋을 pandas로 불러오고 
    apriori 알고리즘으로 구한 값을 list로 저장하고
    파일 쭉 읽으면서 test해봄
    association rule 값이 같을 경우 correct 다를 경우 fail을 print하게 했다.
"""
def test(test_list) :
    print()
    print()
    print("-------------------------test start-------------------------")
    file_path = 'C:/Users/pbk54/Desktop/3학년1학기/빅최기/과제3/groceries.csv'

    with open(file_path, encoding="utf8") as f :
        lines = f.readlines()
    #print(len(lines))

    len_list = []
    for _ in range(len(lines)) :
        len_list.append(len(lines[_].split(',')))

    Counter(len_list)

    f = open(file_path, encoding='utf8')
    reader = csv.reader(f)
    csv_list = []
    for l in reader :
        csv_list.append(l)
    f.close()
    df = pd.DataFrame(csv_list)
    test_start = time.time()
    for j in range(len(test_list)) :
        a = set(test_list[j][0])
        b = set(test_list[j][1])
        c = test_list[j][2]
        cnt1 = 0
        cnt2 = 0
        for i in range(len(df)) :
            if len(a | b | set(df.iloc[i])) == len(set(df.iloc[i])) :
                cnt1 += 1
                cnt2 += 1
            elif len(a| set(df.iloc[i])) == len(set(df.iloc[i])) :
                cnt1 += 1
        if c == round(cnt2/cnt1,2) :
            print(test_list[j], "correct")
        else :
            print("fail")
    test_end = time.time()
    print(f"test run time: {test_end - test_start:.6f} sec")


test(test_list)

