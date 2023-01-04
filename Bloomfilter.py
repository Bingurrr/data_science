import math
import mmh3
import random
class BloomFilter :
  def __init__(self, capacity, fp_prob) :
    self.capacity = capacity
    self.fp_prob = fp_prob
    self.bitarray = 0
    self.n_bits = math.ceil(-math.log(fp_prob, math.e) * capacity / math.log(2,math.e)**2) 
    self.n_hashs = int(self.n_bits / capacity * math.log(2, math.e))
    print(self.n_bits)
    print(self.n_hashs)
    self.seeds = [random.randint(0,999999) for _ in range(self.n_hashs)]
    

  def put(self, item) :
    for i in range(self.n_hashs) :
      pos = mmh3.hash(item, self.seeds[i]) % self.n_bits
      self.bitarray |= (1 << pos)  
      
  
  def test(self, item) :
    for i in range(self.n_hashs) :
      pos = mmh3.hash(item, self.seeds[i]) % self.n_bits
    
      if self.bitarray & (1 << pos) == 0 :
        return False
      
    return True

# def my_hash(x) :
#   return x*31 + 17

# print('a')

string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
prob = 0.1
bloom = BloomFilter(10000, prob)
arr = []
for i in range(10000) :
    a = string[random.randint(0,len(string)-1)]
    b = string[random.randint(0,len(string)-1)]
    c = string[random.randint(0,len(string)-1)]
    d = string[random.randint(0,len(string)-1)]
    e = string[random.randint(0,len(string)-1)]
    f = string[random.randint(0,len(string)-1)]
    s = a+b+c+d+e+f
    bloom.put(s)
    arr.append(s)

print("Random")
fal_cnt = 0
tru_cnt = 0
for i in range(10000) :
    a = string[random.randint(0,len(string)-1)]
    b = string[random.randint(0,len(string)-1)]
    c = string[random.randint(0,len(string)-1)]
    d = string[random.randint(0,len(string)-1)]
    e = string[random.randint(0,len(string)-1)]
    f = string[random.randint(0,len(string)-1)]
    s = a+b+c+d+e+f
    if s in arr :
        print("pass")
        continue
    #print(s, bloom.test(s))
    if (bloom.test(s)) :
        tru_cnt += 1
    else :
        fal_cnt += 1
print("false positive 비율:",prob, "true:", tru_cnt, "false:", fal_cnt)
