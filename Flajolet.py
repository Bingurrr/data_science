from re import A
from pkg_resources import empty_provider
import mmh3
import random
import math
import matplotlib.pyplot as plt
from tqdm import tqdm
from sklearn.metrics import mean_squared_error
import math
# version1
# 예측값 = 2**R
class FM1 :
  def __init__(self, domain_size, seed) :
    #self.bitarray = 0
    self.domain_size = domain_size
    self.n_bits = math.ceil(math.log2(domain_size))
    self.seed = seed
    self.mask = (1 << self.n_bits) - 1
    self.R = 0
  def put(self, item) :
    h = mmh3.hash(item, self.seed) & self.mask
    #print('h', h)
    r = 0
    if h == 0 : return
    i = 0
    while h >= (1 << i) :
        if (h & (1 << i )) == 0 :
            i += 1
        else :
            r = i
            break
    if r > self.R :
        self.R = r
    #print(self.R)
  def size(self) :
    return 2 ** self.R

# version2 
# 예측값 = 2**R/ 0.77351
class FM2 :
  def __init__(self, domain_size, seed) :
    self.bitarray = 0
    self.domain_size = domain_size
    self.n_bits = math.ceil(math.log2(domain_size))
    self.seed = random.randint(0,999999)
    self.mask = (1 << self.n_bits) - 1

  def put(self, item) :
    h = mmh3.hash(item, self.seed) & self.mask
    r = 0
    if h == 0 : return
    while (h & (1 << r )) == 0 : r += 1
    #print('2', r)
    self.bitarray |= (1 << r)
    #print('2', self.bitarray)

  def size(self) :
    R = 0
    while (self.bitarray & (1 << R ) != 0) : R += 1
    return 2 ** R / 0.77351 # version 2 


# upgarde version2 
# 예측값 = 2**R/ 0.77351
class FM3 :
  def __init__(self, domain_size, n_group, n_hash) :
    self.domain_size = domain_size
    self.group_size = domain_size // n_group
    self.group = [[] for _ in range(n_group)]
    self.n_group = n_group
    self.total_group = []  
    self.n_bits = math.ceil(math.log2(domain_size))
    self.n_hash = n_hash
    self.bitarray = [0 for _ in range(n_hash)]
    self.seed_group = [random.randint(0,999999) for _ in range(n_hash)]
    self.mask = (1 << self.n_bits) - 1

  def put(self, item) :
    #ave = 0
    #a = self.bitarray
    #arr = []
    for i in range(self.n_hash) :
        h = mmh3.hash(item, self.seed_group[i]) & self.mask
        r = 0
        if h == 0 : continue
        while (h & (1 << r )) == 0 : r += 1
        self.bitarray[i] |= (1 << r)


  def size(self) :
    self.group = [[] for _ in range(self.n_group)]
    for i in range(self.n_hash) :
        R = 0
        while (self.bitarray[i] & (1 << R ) != 0) : R += 1
        self.group[i%self.n_group].append(2**R/0.77351)
    """
        1 2 3 4 가 있을 경우 중앙값은 (2+3) / 2 = 2.5 지만
        이번 과제를 하면서 중앙값을 정렬되어 있는 리스트에서
        len(리스트) // 2 번째 인덱스의 값으로 구하였다.
    
    """
    # 중앙값 뽑기
    sum = 0
    n = 0
    for x in self.group :
        if len(x) == 0 : continue
        n +=1
        middle = len(x) // 2
        x = sorted(x)
        sum += x[middle]
    
    # 평균구하기
    ret = sum / n
    return  ret # version 2 

# 해시펑션 시드를 같게해서 version1 이랑 version2 비교
seed = random.randint(0,999999)
# version1
fm1 = FM1(100000, seed)
# version2 original
fm2 = FM2(100000, seed)
# 그룹 20개 해쉬함수 100개
fm3 = FM3(100000,20,100)
# 그룹 10개 해쉬함수 100개
fm4 = FM3(100000,10,100)
# 그룹 5개 해쉬함수 100개
fm5 = FM3(100000,5,100)
# 그룹 10개 해쉬함수 30개
fm6 = FM3(100000,10,30)
# 그룹 10개 해쉬함수 50개
fm7 = FM3(100000,10,50)

tset = set()
x = []
fm1_value = []
fm2_value = []
fm3_value = []
fm4_value = []
fm5_value = []
fm6_value = []
fm7_value = []


err_1 = 0
err_2 = 0
err_3 = 0
for i in tqdm(range(10000)) :
  item = str(random.randint(0,100000))
  fm1.put(item)
  fm2.put(item)
  fm3.put(item)
  fm4.put(item)
  fm5.put(item)
  fm6.put(item)
  fm7.put(item)

  tset.add(item)
  x.append(len(tset))
  fm1_value.append(fm1.size())
  fm2_value.append(fm2.size())
  fm3_value.append(fm3.size())
  fm4_value.append(fm4.size())
  fm5_value.append(fm5.size())
  fm6_value.append(fm6.size())
  fm7_value.append(fm7.size())

#RMSE계산
err_1 = math.sqrt(mean_squared_error(x,fm1_value))
err_2 = math.sqrt(mean_squared_error(x,fm2_value))
err_3 = math.sqrt(mean_squared_error(x,fm3_value))
err_4 = math.sqrt(mean_squared_error(x,fm4_value))
err_5 = math.sqrt(mean_squared_error(x,fm5_value))
err_6 = math.sqrt(mean_squared_error(x,fm6_value))
err_7 = math.sqrt(mean_squared_error(x,fm7_value))


plt.bar([0,1,2,3,4,5,6], [err_1, err_2, err_3, err_4, err_5, err_6, err_7])
plt.xticks([0,1,2,3,4,5,6], ['version1', 'version2', 'groups:20 hash:100', 'groups:10 hash:100', 'groups:5 hash:100','groups:10 hash:30','groups:10 hash:50'])
plt.xticks(rotation =30)
plt.show()

print(f"MSE결과: err_1: {err_1}, err_2: {err_2}, err_3: {err_3}")

print(f"version1 true: {len(tset)}, estimated: {fm1.size()}")
plt.title('version 1')
plt.scatter(x,fm1_value)
plt.plot(x,x, color ='r')
plt.show()

print(f"version2 true: {len(tset)}, estimated: {fm2.size()}")
plt.title('version 2')
plt.scatter(x,fm2_value)
plt.plot(x,x, color ='r')
plt.show()

print(f"version3 true: {len(tset)}, estimated: {fm3.size()}")
plt.title('n_groups : 20, n_hash : 100')
plt.scatter(x,fm3_value)
plt.plot(x,x, color ='r')
plt.show()

print(f"version3 true: {len(tset)}, estimated: {fm4.size()}")
plt.title('n_groups : 10, n_hash : 100')
plt.scatter(x,fm4_value)
plt.plot(x,x, color ='r')
plt.show()

print(f"version3 true: {len(tset)}, estimated: {fm5.size()}")
plt.title('n_groups : 5, n_hash : 100')
plt.scatter(x,fm5_value)
plt.plot(x,x, color ='r')
plt.show()

print(f"version3 true: {len(tset)}, estimated: {fm6.size()}")
plt.title('n_groups : 10, n_hash : 30')
plt.scatter(x,fm6_value)
plt.plot(x,x, color ='r')
plt.show()


print(f"version3 true: {len(tset)}, estimated: {fm7.size()}")
plt.title('# n_groups : 10, n_hash : 50 ')
plt.scatter(x,fm7_value)
plt.plot(x,x, color ='r')
plt.show()

