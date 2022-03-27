"""A bit of code to add polygon colliders to a tiled map.
Use the tile id 37 to make tiles that are not colliders, or use a different decorators list, or directly a range."""

from typing import Union


def tiled_topolygon(file_input:str,file_output:str=None,decorators:Union[list[int],range]=None):
    """
    Adds polygons to a tiled map.
    """

    if decorators is None:
        decorators=[0,37]
    with open(file_input, 'r') as file:
        list_lines=file.readlines()
        lines_before=0
        while "</layer>" not in list_lines[lines_before]:
            lines_before+=1
        l_final=list_lines[:lines_before+1]
        lines_before=0
        while "<data" not in list_lines[lines_before]:
            lines_before+=1
        lines_after=lines_before
        while "</data>" not in list_lines[lines_after]:
            lines_after+=1
        l_lines=list_lines[lines_before+1:lines_after]
        for i in range(len(l_lines)):
            l_lines[i]=l_lines[i].replace("\n","").split(",")
            for x in range(len(l_lines[i])):
                l_lines[i][x]=int(l_lines[i][x]) if l_lines[i][x]!="" else 0
        # l_lines=[[0 for j in range(len(list_lines[0].split(",")))] for i in range(len(list_lines))]
        # for index,value in enumerate(l_lines_2):
        #     print(value)
        #     l_lines[index]=value.replace("\n","").split(",")
        #     l_lines[index]=[int(x) for x in l_lines[index] if x!=""]
    l_final.append("""<objectgroup id="2" name="objects">\n""")
    id_polygon=20 # I start at 20, but tiled started at 17 idk why.
    #  it's a security just in case, maybe the id changes if you add other layers
    scale=64
    slope_right=(1,0.2)

    for index,value in enumerate(l_lines):
        for index_2,value_2 in enumerate(value):
            if value_2 not in decorators:
                l_final.append(f"""    <object id="{id_polygon}" x="{scale*index_2+1}" y="{scale*index}">\n""")
                l_final.append(f"""      <polygon points="{0 if is_decorator(l_lines,index_2,index-1,decorators) else -0.8},0 {scale-(2 if is_decorator(l_lines,index_2,index-1,decorators) else 1.2)},0 {scale-1},{(0 if is_decorator(l_lines,index_2,index-1,decorators) else 1) if is_decorator(l_lines,index_2+1,index,decorators) else 0.2} {scale-1},{scale-1} {scale-1.2},{scale} -0.8,{scale} {-1},{scale-1} {-1},{(0 if is_decorator(l_lines,index_2,index-1,decorators) else 1) if is_decorator(l_lines,index_2-1,index,decorators) else 0.2}" />\n""")
                # l_final.append(f"""      <polygon points="0,0 {scale-4},0 {scale-2},2 {scale-2},{scale-2} {scale-4},{scale} 0,{scale} -2,{scale-2} -2,2" />\n""")
                l_final.append("    </object>\n")
                id_polygon+=1
                # print("bon:",end="")
            # print(value_2)
    l_final.append("""  </objectgroup>\n""")
    l_final.append("""</map>\n""")
    # print(l_final)
    if file_output is None:
        file_output="output.tmx"
    with open(file_output, 'w') as file:
        file.writelines(l_final)

def is_decorator(list_values:list,x:int,y:int,decorators:Union[list[int],range]):
    """
    Returns True if the tile is a decorator.
    """
    if y in range(len(list_values)) and x in range(len(list_values[y])):
        return list_values[y][x] in decorators

if __name__ == "__main__":
    tiled_topolygon("map0.tmx","map0.tmx",[0,37])
