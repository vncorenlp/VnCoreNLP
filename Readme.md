#### Table of contents
1. [Introduction](#introduction)
2. [Installation](#install)
2. [Usage for Python users](#python)
3. [Usage for Java users](#java)
4. [Experimental results](#exp)

# VnCoreNLP: A Vietnamese natural language processing toolkit <a name="introduction"></a>

VnCoreNLP is a **fast and accurate** NLP annotation pipeline for Vietnamese, providing rich linguistic annotations through key NLP components of **word segmentation**, **POS tagging**, **named entity recognition** (NER) and **dependency parsing**. Users do not have to install external dependencies. Users can run processing pipelines from either the command-line or the  API. The general architecture and experimental results of VnCoreNLP can be found in the following related papers:

1. Thanh Vu, Dat Quoc Nguyen, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2018**. [VnCoreNLP: A Vietnamese Natural Language Processing Toolkit](http://aclweb.org/anthology/N18-5012). In  *Proceedings of the 2018 Conference of the North American Chapter of the Association for Computational Linguistics: Demonstrations*, [NAACL 2018](http://naacl2018.org), pages 56-60. [[.bib]](http://aclweb.org/anthology/N18-5012.bib)
2. Dat Quoc Nguyen, Dai Quoc Nguyen, Thanh Vu, Mark Dras and Mark Johnson. **2018**. [A Fast and Accurate Vietnamese Word Segmenter](http://www.lrec-conf.org/proceedings/lrec2018/summaries/55.html). In *Proceedings of the 11th International Conference on Language Resources and Evaluation*, [LREC 2018](http://lrec2018.lrec-conf.org/en/), pages 2582-2587. [[.bib]](https://dblp.uni-trier.de/rec/bibtex/conf/lrec/NguyenNVDJ18)
3. Dat Quoc Nguyen, Thanh Vu, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2017**. [From Word Segmentation to POS Tagging for Vietnamese](http://aclweb.org/anthology/U17-1013). In *Proceedings of the 15th Annual Workshop of the Australasian Language Technology Association*, [ALTA 2017](http://alta2017.alta.asn.au), pages 108-113. [[.bib]](http://aclweb.org/anthology/U17-1013.bib)

Please **CITE** paper [1] whenever VnCoreNLP is used to produce published results or incorporated into other software. If you are dealing in depth with either word segmentation or POS tagging, you are also encouraged to cite paper [2] or [3], respectively. 

If you are looking for light-weight versions, VnCoreNLP's word segmentation and POS tagging components have also been released as independent packages [RDRsegmenter](https://github.com/datquocnguyen/RDRsegmenter)  [2]  and [VnMarMoT](https://github.com/datquocnguyen/VnMarMoT) [3], resepectively.


## Installation <a name="install"></a>

- `Python 3.4+` if using [a Python wrapper of VnCoreNLP](https://github.com/dnanhkhoa/python-vncorenlp). To install this wrapper, users have to run the following command:

    `$ pip3 install vncorenlp` 
    
    _A special thanks goes to Khoa Duong ([@dnanhkhoa](https://github.com/dnanhkhoa)) for creating this wrapper!_
    
- `Java 1.8+` 
- File  `VnCoreNLP-1.1.1.jar` (27MB) and folder `models` (115MB) are placed in the same working folder.



## Usage for Python users <a name="python"></a>

Assume that the Python wrapper of VnCoreNLP is already installed via: `$ pip3 install vncorenlp`

### Use as a service

1. Run the following command: 
```
    # To perform word segmentation, POS tagging, NER and then dependency parsing
    $ vncorenlp -Xmx2g <FULL-PATH-to-VnCoreNLP-jar-file> -p 9000 -a "wseg,pos,ner,parse"
    
    # To perform word segmentation, POS tagging and then NER
    # $ vncorenlp -Xmx2g <FULL-PATH-to-VnCoreNLP-jar-file> -p 9000 -a "wseg,pos,ner"
    # To perform word segmentation and then POS tagging
    # $ vncorenlp -Xmx2g <FULL-PATH-to-VnCoreNLP-jar-file> -p 9000 -a "wseg,pos"
    # To perform word segmentation only
    # $ vncorenlp -Xmx500m <FULL-PATH-to-VnCoreNLP-jar-file> -p 9000 -a "wseg"
```

   The service is now available at `http://127.0.0.1:9000`.

2. Use the service in your `python` code:

```python
from vncorenlp import VnCoreNLP
annotator = VnCoreNLP(address="http://127.0.0.1", port=9000) 

# Input 
text = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây."

# To perform word segmentation, POS tagging, NER and then dependency parsing
annotated_text = annotator.annotate(text)   

# To perform word segmentation only
word_segmented_text = annotator.tokenize(text)
```

- `print(annotated_text)` # JSON format

```
{'sentences': [[{'index': 1, 'form': 'Ông', 'posTag': 'Nc', 'nerLabel': 'O', 'head': 4, 'depLabel': 'sub'}, {'index': 2, 'form': 'Nguyễn_Khắc_Chúc', 'posTag': 'Np', 'nerLabel': 'B-PER', 'head': 1, 'depLabel': 'nmod'}, {'index': 3, 'form': 'đang', 'posTag': 'R', 'nerLabel': 'O', 'head': 4, 'depLabel': 'adv'}, {'index': 4, 'form': 'làm_việc', 'posTag': 'V', 'nerLabel': 'O', 'head': 0, 'depLabel': 'root'}, {'index': 5, 'form': 'tại', 'posTag': 'E', 'nerLabel': 'O', 'head': 4, 'depLabel': 'loc'}, {'index': 6, 'form': 'Đại_học', 'posTag': 'N', 'nerLabel': 'B-ORG', 'head': 5, 'depLabel': 'pob'}, {'index': 7, 'form': 'Quốc_gia', 'posTag': 'N', 'nerLabel': 'I-ORG', 'head': 6, 'depLabel': 'nmod'}, {'index': 8, 'form': 'Hà_Nội', 'posTag': 'Np', 'nerLabel': 'I-ORG', 'head': 6, 'depLabel': 'nmod'}, {'index': 9, 'form': '.', 'posTag': 'CH', 'nerLabel': 'O', 'head': 4, 'depLabel': 'punct'}], [{'index': 1, 'form': 'Bà', 'posTag': 'Nc', 'nerLabel': 'O', 'head': 9, 'depLabel': 'sub'}, {'index': 2, 'form': 'Lan', 'posTag': 'Np', 'nerLabel': 'B-PER', 'head': 1, 'depLabel': 'nmod'}, {'index': 3, 'form': ',', 'posTag': 'CH', 'nerLabel': 'O', 'head': 1, 'depLabel': 'punct'}, {'index': 4, 'form': 'vợ', 'posTag': 'N', 'nerLabel': 'O', 'head': 1, 'depLabel': 'nmod'}, {'index': 5, 'form': 'ông', 'posTag': 'Nc', 'nerLabel': 'O', 'head': 4, 'depLabel': 'nmod'}, {'index': 6, 'form': 'Chúc', 'posTag': 'Np', 'nerLabel': 'B-PER', 'head': 5, 'depLabel': 'nmod'}, {'index': 7, 'form': ',', 'posTag': 'CH', 'nerLabel': 'O', 'head': 1, 'depLabel': 'punct'}, {'index': 8, 'form': 'cũng', 'posTag': 'R', 'nerLabel': 'O', 'head': 9, 'depLabel': 'adv'}, {'index': 9, 'form': 'làm_việc', 'posTag': 'V', 'nerLabel': 'O', 'head': 0, 'depLabel': 'root'}, {'index': 10, 'form': 'tại', 'posTag': 'E', 'nerLabel': 'O', 'head': 9, 'depLabel': 'loc'}, {'index': 11, 'form': 'đây', 'posTag': 'P', 'nerLabel': 'O', 'head': 10, 'depLabel': 'pob'}, {'index': 12, 'form': '.', 'posTag': 'CH', 'nerLabel': 'O', 'head': 9, 'depLabel': 'punct'}]]}
```

- `print(word_segmented_text)`

```
[['Ông', 'Nguyễn_Khắc_Chúc', 'đang', 'làm_việc', 'tại', 'Đại_học', 'Quốc_gia', 'Hà_Nội', '.'], ['Bà', 'Lan', ',', 'vợ', 'ông', 'Chúc', ',', 'cũng', 'làm_việc', 'tại', 'đây', '.']]
```




### Use without the service

```python
from vncorenlp import VnCoreNLP

# To perform word segmentation, POS tagging, NER and then dependency parsing
annotator = VnCoreNLP("<FULL-PATH-to-VnCoreNLP-jar-file>", annotators="wseg,pos,ner,parse", max_heap_size='-Xmx2g') 

# To perform word segmentation, POS tagging and then NER
# annotator = VnCoreNLP("<FULL-PATH-to-VnCoreNLP-jar-file>", annotators="wseg,pos,ner", max_heap_size='-Xmx2g') 
# To perform word segmentation and then POS tagging
# annotator = VnCoreNLP("<FULL-PATH-to-VnCoreNLP-jar-file>", annotators="wseg,pos", max_heap_size='-Xmx2g') 
# To perform word segmentation only
# annotator = VnCoreNLP("<FULL-PATH-to-VnCoreNLP-jar-file>", annotators="wseg", max_heap_size='-Xmx500m') 
    
# Input 
text = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây."

# To perform word segmentation, POS tagging, NER and then dependency parsing
annotated_text = annotator.annotate(text)

# To perform word segmentation only
word_segmented_text = annotator.tokenize(text) 

```


## Usage for Java users <a name="java"></a>

### Using VnCoreNLP from the command line

You can run VnCoreNLP to annotate an input raw text corpus (e.g. a collection of news content) by using following commands:

    // To perform word segmentation, POS tagging, NER and then dependency parsing
    $ java -Xmx2g -jar VnCoreNLP-1.1.1.jar -fin input.txt -fout output.txt
    // To perform word segmentation, POS tagging and then NER
    $ java -Xmx2g -jar VnCoreNLP-1.1.1.jar -fin input.txt -fout output.txt -annotators wseg,pos,ner
    // To perform word segmentation and then POS tagging
    $ java -Xmx2g -jar VnCoreNLP-1.1.1.jar -fin input.txt -fout output.txt -annotators wseg,pos
    // To perform word segmentation
    $ java -Xmx2g -jar VnCoreNLP-1.1.1.jar -fin input.txt -fout output.txt -annotators wseg    


### Using VnCoreNLP from the API

The following code is a simple and complete example:

```java
import vn.pipeline.*;
import java.io.*;
public class VnCoreNLPExample {
    public static void main(String[] args) throws IOException {
    
        // "wseg", "pos", "ner", and "parse" refer to as word segmentation, POS tagging, NER and dependency parsing, respectively. 
        String[] annotators = {"wseg", "pos", "ner", "parse"}; 
        VnCoreNLP pipeline = new VnCoreNLP(annotators); 
    
        String str = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây."; 
        
        Annotation annotation = new Annotation(str); 
        pipeline.annotate(annotation); 
        
        System.out.println(annotation.toString());
        // 1    Ông                 Nc  O       4   sub 
        // 2    Nguyễn_Khắc_Chúc    Np  B-PER   1   nmod
        // 3    đang                R   O       4   adv
        // 4    làm_việc            V   O       0   root
        // ...
        
        //Write to file
        PrintStream outputPrinter = new PrintStream("output.txt");
        pipeline.printToFile(annotation, outputPrinter); 
    
        // You can also get a single sentence to analyze individually 
        Sentence firstSentence = annotation.getSentences().get(0);
        System.out.println(firstSentence.toString());
    }
}
```

<img width="1039" alt="vncorenlpexample" src="https://user-images.githubusercontent.com/33695776/37561346-aca1fd68-2aa0-11e8-8bd8-530577b0b5cf.png">

See VnCoreNLP's open-source in folder `src` for API details. 

## Experimental results <a name="exp"></a>

See details in papers [1,2,3] above or at [NLP-progress](http://nlpprogress.com/vietnamese/vietnamese.html).

