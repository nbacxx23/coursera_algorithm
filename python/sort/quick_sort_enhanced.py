class QuickX():
    __INSERTION_SORT_CUTOFF = 8
    
    def __init__(self):
        pass
    
    # is v < w ?
    @staticmethod
    def __less(v,w):
        return v < w
    
    def sort(self,a):
        self.__sort(a,0,len(a)-1)
        assert self.is_sorted(a)
        
    def __sort(self,a,lo,hi):
        if hi <= lo:
            return
        
        n = hi -lo +1
        #cutoff to insertion sort (Insertion.sort() uses half-open intervals)
        if n<=self.__INSERTION_SORT_CUTOFF:
            self.__insertion_sort(a,lo,hi)
            return
        
        j = self.__partition(a,lo,hi)
        self.__sort(a,lo,j-1)
        self.__sort(a,j+1,hi)
            
    def __insertion_sort(self,a,lo,hi):
        i = lo
        while i <= hi:
            j = i
            while j > lo and self.__less(a[j],a[j-1]):
                a[j],a[j-1] = a[j-1],a[j]
                j -= 1
            i += 1
            
    def __partition(self,a,lo,hi):
        n = hi -lo +1
        m = self.__median3(a,lo,lo+n//2,hi)
        a[m],a[lo] = a[lo],a[m]
        
        i = lo
        j = hi + 1
        v = a[lo]
        # a[lo] is unique largest element
        i += 1
        while self.__less(a[i],v):
            if i == hi:
                a[lo],a[hi] = a[hi],a[lo]
                return hi
            i += 1
            
        # a[lo] is unique smallest element
        j -= 1
        while self.__less(v, a[j]):
            if j == lo + 1:
                return lo
            j -= 1
            
        while i<j:
            a[i],a[j] = a[j],a[i]
            i += 1
            while self.__less(a[i],v): i += 1
            j -= 1
            while self.__less(v,a[j]): j -= 1
        
        a[lo],a[j] = a[j],a[lo]
        return j
    
    def __median3(self,a,i,j,k):
        if self.__less(a[i],a[j]):
            if self.__less(a[j],a[k]):
                return j
            elif self.__less(a[i],a[k]):
                return k
            else:
                return i
        else:
            if self.__less(a[k],a[j]):
                return j
            elif self.__less(a[k],a[i]):
                return k
            else:
                return i
            
    def is_sorted(self,a):
        return self.__is_sorted(a,0,len(a)-1)
    
    def __is_sorted(self,a,lo,hi):
        i = lo +1
        while i<=hi:
            if self.__less(a[i],a[i-1]):
                return False
            i += 1
        return True