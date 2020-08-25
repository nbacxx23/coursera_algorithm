class Merge():
    def __init__(self):
        pass
    
    @staticmethod
    def __less(v,w):
        return v<w
    
    def __is_sorted(self,a,lo,hi):
        i = lo + 1
        while i<=hi:
            if self.__less(a[i],a[i-1]):
                return False
            i += 1
        return True
    
    def __merge(self,a,aux,lo,mid,hi):
        # precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert self.__is_sorted(a, lo, mid)
        assert self.__is_sorted(a, mid+1, hi)
    
        # copy to aux[]
        for i in range(len(a)):
            aux[i] = a[i]
        
        # merge back to a[]
        i = lo
        j = mid + 1
        k = lo
        while k<= hi:
            if i > mid:
                a[k] = aux[j]
                j += 1
            elif j > hi:
                a[k] = aux[i]
                i += 1
            elif self.__less(aux[j],aux[i]):
                a[k] = aux[j]
                j += 1
            else:
                a[k] = aux[i]
                i += 1
            k += 1
        assert self.__is_sorted(a,lo,hi)
        
    #mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    def __sort(self,a,aux,lo,hi):
        if hi <= lo:
            return
        mid = lo + (hi - lo)//2
        self.__sort(a,aux,lo,mid)
        self.__sort(a,aux,mid+1,hi)
        self.__merge(a,aux,lo,mid,hi)
        
    def sort(self,a):
        aux = [None]*len(a)
        self.__sort(a,aux,0,len(a)-1)
        assert self.is_sorted(a)
    
    def is_sorted(self,a):
        return self.__is_sorted(a, 0, len(a) - 1)
    
    def __merge_index(self,a,index,aux,lo,mid,hi):
        k = lo
        while k<= hi:
            aux[k] = index[k]
            k += 1
        i = lo
        j = mid + 1
        k = lo
        while k<=hi:
            if i > mid:
                index[k] = aux[j]
                j += 1
            elif j > hi:
                index[k] = aux[i]
                i += 1
            elif self.__less(a[aux[j]],a[aux[i]]):
                index[k] = aux[j]
                j += 1
            else:
                index[k] = aux[i]
                i += 1
            k += 1
            
    def __index_sort(self,a,index,aux,lo,hi):
        if hi <= lo:
            return
        mid = lo + (hi-lo)//2
        self.__index_sort(a,index,aux,lo,mid)
        self.__index_sort(a,index,aux,mid + 1,hi)
        self.__merge_index(a,index,aux,lo,mid,hi)
        
            
    def index_sort(self,a):
        n = len(a)
        index = [None]*n
        for i in range(n):
            index[i] = i
        
        aux = [None]*n
        self.__index_sort(a,index,aux,0,n-1)
        return index