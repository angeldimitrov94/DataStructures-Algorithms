import sys


def karatsuba(num1, num2):
    print(f"\tnum1/2:[{num1}/{num2}]len of 1/2[{len(num1)}/{len(num2)}]")
    if len(num1) == 1 or len(num2) == 1:
      return int(float(num1) * float(num2))
    else:
      a = firstHalf(num1)
      print(f"a:{a}")
      b = secondHalf(num1)
      print(f"b:{b}")
      c = firstHalf(num2)
      print(f"c:{c}")
      d = secondHalf(num2)
      print(f"d:{d}")
      p = str(int(float(a) + float(b)))
      print(f"p:{p}")
      q = str(int(float(c) + float(d)))
      print(f"q:{q}")
      ac = karatsuba(a, c)
      print(f"ac:{ac}")
      bd = karatsuba(b, d)
      print(f"bd:{bd}")
      pq = karatsuba(p, q)
      print(f"pq:{pq}")
      adbc = pq - ac - bd
      print(f"adbc:{adbc}")
      return int(10 ** (len(num1)) * ac + 10 ** (len(num1)/2) * adbc + bd)

def firstHalf(num):
  return num[0:int(len(num) / 2)]

def secondHalf(num):
  return num[int(len(num) / 2):int(len(num))]

if __name__ == '__main__':
    num1 = sys.argv[1]
    num2 = sys.argv[2]
    print(f"karatsuba of {num1} and {num2} = {karatsuba(num1,num2)}")