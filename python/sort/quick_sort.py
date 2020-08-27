import random
class Quick():
    def __init__(self):
        pass
    
    def sort(self,a):
        random.shuffle(a)
        self.__sort(a,0,len(a)-1)
        assert self.is_sorted(a)
        
    #quicksort the subarray from a[lo] to a[hi]
    def __sort(self,a,lo,hi):
        if hi <= lo:
            return
        j = self.__partition(a,lo,hi)
        self.__sort(a,lo,j-1)
        self.__sort(a,j+1,hi)
        assert self.__is_sorted(a,lo,hi)
    
    # partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    # and return the index j.
    def __partition(self,a,lo,hi):
        i = lo
        j = hi +1
        v = a[lo]
        while True:
            # find item on lo to swap
            i += 1
            while self.__less(a[i],v):
                if i ==hi:
                    break
                i += 1
                    
            # find item on hi to swap
            j -= 1
            while self.__less(v,a[j]):
                if j==lo:
                    break
                j -= 1
            
            # check if pointers cross
            if i >= j:
                break
                
            a[i],a[j] = a[j],a[i]
            
        # put partitioning item v at a[j]
        a[lo],a[j] = a[j], a[lo]
        
        # now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j
    
    def select(self,a,k):
        if k<0 or k >= len(a):
            print ("index is not between 0 and " + len(a) + ": " + k)
        random.shuffle(a)
        lo = 0
        hi = len(a)-1
        while hi > lo:
            i = self.__partition(a,lo,hi)
            if i > k: hi = i -1
            elif i < k: lo = i + 1
            else:
                return a[i]
        return a[lo]
    
    # is v < w ?
    @staticmethod
    def __less(v,w):
        return v < w
    
    
    def is_sorted(self,a):
        return self.__is_sorted(a,0,len(a)-1)
    
    def __is_sorted(self,a,lo,hi):
        i = lo +1
        while i<=hi:
            if self.__less(a[i],a[i-1]):
                return False
            i += 1
        return True