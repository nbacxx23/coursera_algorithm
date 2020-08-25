class MergeX():
    __CUTOFF = 7
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
    
    def __merge(self,src,dst,lo,mid,hi):
        assert self.__is_sorted(src,lo,mid)
        assert self.__is_sorted(src,mid+1,hi)
        
        i = lo
        j = mid + 1
        k = lo
        while k <= hi:
            if i > mid:
                dst[k] = src[j]
                j += 1
            elif j > hi:
                dst[k] = src[i]
                i += 1
            elif self.__less(src[j],src[i]):
                dst[k] = src[j]
                j += 1
            else:
                dst[k] = src[i]
                i += 1
            k += 1
        
        assert self.__is_sorted(dst,lo,hi)
        
    def __sort(self,src,dst,lo,hi):
        if (hi<= lo + self.__CUTOFF):
            self.__insertion_sort(dst,lo,hi)
            return
        mid = lo + (hi-lo)//2
        self.__sort(dst,src,lo,mid)
        self.__sort(dst,src,mid+1,hi)
        
        if not self.__less(src[mid+1],src[mid]):
            i = lo
            while i <= hi:
                dst[i] = src[i]
                i += 1
            return
        
        self.__merge(src, dst, lo, mid, hi)
        
    def sort(self,a):
        aux = a.copy()
        self.__sort(aux,a,0,len(a)-1)
        assert self.is_sorted(a)
    
    def __insertion_sort(self,a,lo,hi):
        i = lo
        while i <= hi:
            j = i
            while j > lo and self.__less(a[j],a[j-1]):
                a[j],a[j-1] = a[j-1],a[j]
                j -= 1
            i += 1
    
    def is_sorted(self,a):
        return self.__is_sorted(a, 0, len(a) - 1)