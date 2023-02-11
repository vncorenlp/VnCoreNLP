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

- `Java 1.8+` (Prerequisite)
- File  `VnCoreNLP-1.2.jar` (27MB) and folder `models` (115MB) are placed in the same working folder.
- `Python 3.6+` if using [a Python wrapper of VnCoreNLP](https://github.com/thelinhbkhn2014/VnCoreNLP_Wrapper). To install this wrapper, users have to run the following command:

    `$ pip3 install py_vncorenlp` 
    
    _A special thanks goes to [Linh The Nguyen](https://github.com/thelinhbkhn2014) for creating this wrapper!_
    
    
## Usage for Python users <a name="python"></a>

```python
import py_vncorenlp

# Automatically download VnCoreNLP components from the original repository
# and save them in some local working folder
py_vncorenlp.download_model(save_dir='/absolute/path/to/vncorenlp')

# Load VnCoreNLP from the local working folder that contains both `VnCoreNLP-1.2.jar` and `models` 
model = py_vncorenlp.VnCoreNLP(save_dir='/absolute/path/to/vncorenlp')
# Equivalent to: model = py_vncorenlp.VnCoreNLP(annotators=["wseg", "pos", "ner", "parse"], save_dir='/absolute/path/to/vncorenlp')

# Annotate a raw corpus
model.annotate_file(input_file="/absolute/path/to/input/file", output_file="/absolute/path/to/output/file")

# Annotate a raw text
model.print_out(model.annotate_text("Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây."))
```

By default, the output is formatted with 6 columns representing word index, word form, POS tag, NER label, head index of the current word and its dependency relation type:

```
1       Ông     Nc      O       4       sub
2       Nguyễn_Khắc_Chúc        Np      B-PER   1       nmod
3       đang    R       O       4       adv
4       làm_việc        V       O       0       root
5       tại     E       O       4       loc
6       Đại_học N       B-ORG   5       pob
...
```

For users who use VnCoreNLP only for word segmentation:

```python
rdrsegmenter = py_vncorenlp.VnCoreNLP(annotators=["wseg"], save_dir='/absolute/path/to/vncorenlp')
text = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây."
output = rdrsegmenter.word_segment(text)
print(output)
# ['Ông Nguyễn_Khắc_Chúc đang làm_việc tại Đại_học Quốc_gia Hà_Nội .', 'Bà Lan , vợ ông Chúc , cũng làm_việc tại đây .']
```



## Usage for Java users <a name="java"></a>

### Using VnCoreNLP from the command line

You can run VnCoreNLP to annotate an input raw text corpus (e.g. a collection of news content) by using following commands:

    // To perform word segmentation, POS tagging, NER and then dependency parsing
    $ java -Xmx2g -jar VnCoreNLP-1.2.jar -fin input.txt -fout output.txt
    // To perform word segmentation, POS tagging and then NER
    $ java -Xmx2g -jar VnCoreNLP-1.2.jar -fin input.txt -fout output.txt -annotators wseg,pos,ner
    // To perform word segmentation and then POS tagging
    $ java -Xmx2g -jar VnCoreNLP-1.2.jar -fin input.txt -fout output.txt -annotators wseg,pos
    // To perform word segmentation
    $ java -Xmx2g -jar VnCoreNLP-1.2.jar -fin input.txt -fout output.txt -annotators wseg    


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

