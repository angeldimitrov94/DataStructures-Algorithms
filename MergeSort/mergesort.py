import sys


def mergesort(inList):
  """Recursive implementation of merge sort

  Args:
      inList (list): list to be merge-sorted

  Returns:
      (list): sorted list
  """
    if len(inList) < 2:
      #if list is length 1, return it
      return inList
    else:
      #split the list into first and second parts
      first, second = split(inList)
      #sort first half and return results into first object to update it for use in merge
      first = mergesort(first)
      #sort second half and return results into second object to update it for use in merge
      second = mergesort(second)
      #merge the first and second lists and return it
      return merge(first,second)
      
def split(inList):
  """Split the list into 2 parts at len(arr)/2
  Note : first list will always be shorter for odd-length lists

  Args:
      inList (list): list to be split

  Returns:
      (tuple of 2 lists): first, second parts of the split list
  """
  midpoint = int(len(inList) / 2)
  end = len(inList)
  first = inList[0:midpoint]
  second = inList[midpoint:end]
  return first, second

def merge(inList1, inList2):
  """Merge the two lists in order

  Args:
      inList1 (list): sorted list 1
      inList2 (list): sorted list 2

  Returns:
      (list): sorted and merged list combining list 1 and 2
  """
  i = 0 #for inList1
  j = 0 #for inList2
  merged = []
  for k in range(0, len(inList1) + len(inList2)):
    #if both counters i and j are still smaller than
    #size of both inList1 and inList2
    if len(inList1) > i and len(inList2) > j:
      if (inList1[i] < inList2[j]):
        #inList1 item smaller than inList2
        merged.append(inList1[i])
        i += 1
      else:
        #inList2 item smaller than inList1
        merged.append(inList2[j])
        j += 1
    elif len(inList1) > i:
      #reached/exceeded end of inList1
      merged.append(inList1[i])
      i += 1
    elif len(inList2) > j:
      #reached/exceeded end of inList2
      merged.append(inList2[j])
      j += 1

  return merged


if __name__ == '__main__':
    unsorted = sys.argv[1].replace("[", "").replace("]", "").split(',')
    # unsorted = []
    # temp = ""
    # for character in inpt:
    #   if (character != ','):
    #     temp += character
    #   else:
    #     unsorted.append(temp)
    #     temp = ""       
    # unsorted.append(temp)

    # unsorted = [5,47,35,51,12,17,19]


    print(f"unsorted:{unsorted}")
    sortedList = mergesort(unsorted)
    print(f"sorted:{sortedList}")