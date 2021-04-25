import sys
from timeit import default_timer as timer

      
def SortAndCountInv(a):
    """Sort and count inversions in array 'a'

    Args:
        a (list): list of ints

    Returns:
        (list,int): tuple of the sorted version of 'a' and # of inversions counted
    """
    n = len(a)
    if(n <= 1):
        return (a,0)
    else:
        (c,leftInv) = SortAndCountInv(a[0 : n // 2])
        (d,rightInv) = SortAndCountInv(a[n // 2 : n])
        (b,splitInv) = MergeAndCountSplitInv(c,d)
        return (b,leftInv+rightInv+splitInv)

def MergeAndCountSplitInv(c,d):
    """Merge c,d count inversions meanwhile

    Args:
        c (list): sorted left side of original array
        d (list): sorted right side of original array

    Returns:
        (list,int): tuple of the sorted version of 'a' and # of inversions 
        counted
    """
    i = 0
    j = 0
    splitInv = 0
    n = len(c)+len(d)
    b = []
    for k in range(n):
        if len(c) > i and len(d) > j:
            if (c[i] < d[j]):
                #c item smaller than d
                b.append(c[i])
                i += 1
            else:
                #d item smaller than c
                b.append(d[j])
                j += 1
                splitInv += (n // 2) - i
        elif len(c) > i:
            #reached/exceeded end of c
            b.append(c[i])
            i += 1
        elif len(d) > j:
            #reached/exceeded end of d
            b.append(d[j])
            j += 1
            
    return (b,splitInv)
    


if __name__ == '__main__':
    #get list of items from args and use it as input
    # inputs = sys.argv[1].replace("[", "").replace("]", "").split(',')

    # inputs = [6,5,4,3,2,1]
    file = open(".\input.txt", "r")
    inputs = file.read().split(',')
    intInputs = []
    for item in inputs:
        intInputs.append(int(item))
        
    start = timer()
    outputs = SortAndCountInv(intInputs)
    end = timer()

    print(f"# of inversions : {outputs[1]}...executed in {end-start} seconds")