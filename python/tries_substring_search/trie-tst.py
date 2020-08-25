class TST(object):
    def __init__(self):
        self.__n = 0
        self.__root = None
        
    def size(self):
        return self.__n
    
    def contains(self,key):
        if not key:
            print ("argument to contains() is null")
        return self.get(key) is not None
    
    def get(self,key):
        if key is None:
            print ("calls get() with null argument")
        if len(key)==0:
            print ("key must have length >= 1")
        node_x = self.__get(self.__root,key,0)
        if node_x is None:
            return None
        return node_x.val
    
    def __get(self,node_x,key,d):
        if node_x is None:
            return None
        if len(key)==0:
            print ("key must have length >= 1")
        c = key[d]
        if c < node_x.char:
            return self.__get(node_x.left_node,key,d)
        elif c > node_x.char:
            return self.__get(node_x.right_node,key,d)
        elif d < len(key) - 1:
            return self.__get(node_x.mid_node, key, d+1)
        else:
            return node_x
        
    def put(self,key,val):
        if key is None:
            print ("calls put() with null key")
        if not self.contains(key):
            self.__n += 1
        elif val is None:
            self.__n -= 1
        self.__root = self.__put(self.__root,key,val,0)
        
    def __put(self,node_x,key,val,d):
        c = key[d]
        if node_x is None:
            node_x = Node()
            node_x.char = c
        if c < node_x.char:
            node_x.left_node = self.__put(node_x.left_node, key, val,d)
        elif c > node_x.char:
            node_x.right_node = self.__put(node_x.right_node,key,val,d)
        elif d < len(key)-1:
            node_x.mid_node = self.__put(node_x.mid_node,key,val,d+1)
        else:
            node_x.val = val
        return node_x
    
    def longest_prefix_of(self,query):
        if not query:
            print ("calls longest_prefix_of() with null argument")
        if len(query)==0:
            return None
        length = 0
        node_x = self.__root
        i = 0
        while node_x and i < len(query):
            c = query[i]
            if c < node_x.char:
                node_x = node_x.left_node
            elif c > node_x.char:
                node_x = node_x.right_node
            else:
                i += 1
                if node_x.val != None:
                    length = i
                node_x = node_x.mid_node
        return query[0:length]
    
    def keys(self):
        queue = []
        self.__collect_prefix(self.__root,"",queue)
        return queue
    
    def keys_with_prefix(self,prefix):
        if not prefix:
            print ("calls keys_with_prefix() with null argument")
        queue = []
        node_x = self.__get(self.__root,prefix,0)
        if node_x is None:
            return queue
        if node_x.val is not None:
            queue.append(prefix)
        self.__collect_prefix(node_x.mid_node,prefix,queue)
        return queue
        
    def __collect_prefix(self,node_x,prefix,queue):
        if node_x is None:
            return
        self.__collect_prefix(node_x.left_node,prefix,queue)
        if node_x.val is not None:
            queue.append(prefix+node_x.char)
        self.__collect_prefix(node_x.mid_node,prefix+node_x.char,queue)
        self.__collect_prefix(node_x.right_node,prefix,queue)
        
    def keys_that_match(self,pattern):
        queue = []
        self.__collect_match(self.root,"",0,pattern,queue)
        return queue
    
    def __collect_match(self,node_x,prefix,i,pattern,queue):
        if node_x is None:
            return
        c = pattern[i]
        if c == "." or c < node_x.char:
            self.__collect_match(node_x.left_node,prefix,i,pattern,queue)
        if c == "." or c == node_x.char:
            if i == len(pattern)-1 and node_x.val is not None:
                queue.append(prefix + node_x.char)
            if i < len(pattern) - 1:
                self.__collect_match(node_x.mid_node,prefix+node_x.char,i+1,pattern,queue)
                prefix = prefix[:-1]
        if c == "." or c > node_x.char:
            self.__collect_match(node_x.right_node,prefix,i,pattern,queue)
    
class Node(object):
    def __init__(self):
        self.char = None
        self.val = None
        self.left_node = None
        self.mid_node = None
        self.right_node = None