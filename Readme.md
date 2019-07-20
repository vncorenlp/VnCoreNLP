#### Table of contents
1. [Introduction](#introduction)
2. [Installation](#install)
2. [Usage for Python users](#python)
3. [Usage for Java users](#java)
4. [Experimental results](#exp)

# VnCoreNLP: A Vietnamese natural language processing toolkit <a name="introduction"></a>

VnCoreNLP is a Java NLP annotation pipeline for Vietnamese, providing rich linguistic annotations through key NLP components of **word segmentation**, **POS tagging**, **named entity recognition** (NER) and **dependency parsing**:

* **ACCURATE** – VnCoreNLP is the most accurate toolkit for Vietnamese NLP, obtaining state-of-the-art results on standard benchmark datasets.
* **FAST** – VnCoreNLP is fast, so it can be used for dealing with large-scale data.
* **Easy-To-Use** – Users do not have to install external dependencies. Users can run processing pipelines from either the command-line or the  API.

**The general architecture and experimental results of VnCoreNLP can be found in the following related papers:**

1. Thanh Vu, Dat Quoc Nguyen, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2018**. [VnCoreNLP: A Vietnamese Natural Language Processing Toolkit](http://aclweb.org/anthology/N18-5012). In 	*Proceedings of the 2018 Conference of the North American Chapter of the Association for Computational Linguistics: Demonstrations*, [NAACL 2018](http://naacl2018.org), pages 56-60. [[.bib]](http://aclweb.org/anthology/N18-5012.bib)
2. Dat Quoc Nguyen, Dai Quoc Nguyen, Thanh Vu, Mark Dras and Mark Johnson. **2018**. [A Fast and Accurate Vietnamese Word Segmenter](http://www.lrec-conf.org/proceedings/lrec2018/summaries/55.html). In *Proceedings of the 11th International Conference on Language Resources and Evaluation*, [LREC 2018](http://lrec2018.lrec-conf.org/en/), pages 2582-2587. [[.bib]](https://dblp.uni-trier.de/rec/bibtex/conf/lrec/NguyenNVDJ18)
3. Dat Quoc Nguyen, Thanh Vu, Dai Quoc Nguyen, Mark Dras and Mark Johnson. **2017**. [From Word Segmentation to POS Tagging for Vietnamese](http://aclweb.org/anthology/U17-1013). In *Proceedings of the 15th Annual Workshop of the Australasian Language Technology Association*, [ALTA 2017](http://alta2017.alta.asn.au), pages 108-113. [[.bib]](http://aclweb.org/anthology/U17-1013.bib)

Please **CITE** paper [1] whenever VnCoreNLP is used to produce published results or incorporated into other software. If you are dealing in depth with either word segmentation or POS tagging, you are encouraged to also cite paper [2] or [3], respectively. 

If you are looking for light-weight versions, VnCoreNLP's word segmentation and POS tagging components have also been released as independent packages [RDRsegmenter](https://github.com/datquocnguyen/RDRsegmenter)  [2]  and [VnMarMoT](https://github.com/datquocnguyen/VnMarMoT) [3], resepectively.

_A special thanks goes to Khoa Duong  (@dnanhkhoa) for creating a Python wrapper of VnCoreNLP!_

## Installation <a name="install"></a>

- `Python 3.4+` if using the Python wrapper. To install the wrapper, users have to run the following command:

    ``$ pip3 install vncorenlp`` 

- `Java 1.8+` 
- File  `VnCoreNLP-1.1.jar` (27MB) and folder `models` (115MB) are placed in the same working folder.



## Usage for Python users <a name="python"></a>

_Assume that the Python wrapper of VnCoreNLP is already installed via: ``$ pip3 install vncorenlp``_

### Use as a service (recommended)

1. Run the following command: 

    ``$ vncorenlp -Xmx2g <FULL-PATH-to-VnCoreNLP-jar-file> -p 9000 -a "wseg,pos,ner,parse"``
    
    The service is now available at ``http://127.0.0.1:9000``.

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
annotator = VnCoreNLP("<FULL-PATH-to-VnCoreNLP-jar-file>") 
text = "Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây."
annotated_text = annotator.annotate(text)
word_segmented_text = annotator.tokenize(text) 

```

_For more details, we refer users to [https://github.com/dnanhkhoa/python-vncorenlp](https://github.com/dnanhkhoa/python-vncorenlp)._


## Usage for Java users <a name="java"></a>

### Using VnCoreNLP from the command line

You can run VnCoreNLP to annotate an input raw text corpus (e.g. a collection of news content) by using following commands:

    // To perform word segmentation, POS tagging, NER and then dependency parsing
    $ java -Xmx2g -jar VnCoreNLP-1.1.jar -fin input.txt -fout output.txt
    // To perform word segmentation, POS tagging and then NER
    $ java -Xmx2g -jar VnCoreNLP-1.1.jar -fin input.txt -fout output.txt -annotators wseg,pos,ner
    // To perform word segmentation and then POS tagging
    $ java -Xmx2g -jar VnCoreNLP-1.1.jar -fin input.txt -fout output.txt -annotators wseg,pos
    // To perform word segmentation
    $ java -Xmx2g -jar VnCoreNLP-1.1.jar -fin input.txt -fout output.txt -annotators wseg    


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

We briefly present experimental setups and obtained results in the following subsections. See details in papers [1,2,3] above.

### Word segmentation 

* Training data: 75k manually word-segmented training sentences from the VLSP 2013 word segmentation shared task.
* Test data: 2120 test sentences from the VLSP 2013 POS tagging shared task.

<table>
  <tr>
    <td><b>Model<b></td>
    <td><b>F1 (%)</td>
    <td><b>Speed</b> (words/second)</td>
  </tr>
  <tr>
    <td>VnCoreNLP (i.e. RDRsegmenter)</td>
    <td><b>97.90</b></td>
    <td><b>62k</b> / _</td>
  </tr>
  <tr>
    <td>UETsegmenter</td>
    <td>97.87</td>
    <td>48k / 33k*</td>
  </tr>
   <tr>
    <td>vnTokenizer</td>
    <td>97.33</td>
    <td> _ / 5k*</td>
  </tr>
   <tr>
    <td>JVnSegmenter-Maxent</td>
    <td>97.00</td>
    <td> _ / 1k*</td>
  </tr>
   <tr>
    <td>JVnSegmenter-CRFs</td>
    <td>97.06</td>
    <td> _ / 1k*</td>
  </tr>
   <tr>
    <td>DongDu</td>
    <td>96.90</td>
    <td> _ / 17k*</td>
  </tr>
</table>

* Speed is computed on a personal computer of Intel Core i7 2.2 GHz, except when specifically mentioned. \* denotes that the speed is computed on a personal computer of   Intel Core i5 1.80 GHz.
* See paper [2] for more details.

### POS tagging 

* 27,870 sentences for training and development from the VLSP 2013 POS tagging shared task:
  *  27k sentences are used for training.
  *  870 sentences are used for development.
* Test data: 2120 test sentences from the VLSP 2013 POS tagging shared task.

<table>
    <tr>
    <td><b>Model<b></td>
    <td><b>Accuracy (%)</td>
    <td><b>Speed</td>
  </tr>
  <tr>
    <td>VnCoreNLP (i.e. VnMarMoT)</td>
    <td><b>95.88</b></td>
    <td>25k</td>
  </tr>
  <tr>
    <td>RDRPOSTagger</td>
    <td>   95.11 </td>
    <td> <b>  180k</td>
  </tr>
   <tr>
    <td>BiLSTM-CRF</td>
    <td>95.06</td>
    <td> 3k</td>
  </tr>
   <tr>
    <td>BiLSTM-CRF + CNN-char</td>
    <td>95.40</td>
    <td> 2.5k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + LSTM-char</td>
    <td>95.31</td>
    <td> 1.5k</td>
  </tr>
</table>

* See paper [3] for more details.

### Named entity recognition
* 16,861 sentences for training and development from the VLSP 2016 NER shared task:
  *  14,861 sentences are used for training.
  *  2k sentences are used for development.
* Test data: 2,831 test sentences from the VLSP 2016 NER  shared task.
* **NOTE** that in the VLSP 2016 NER data, each word representing a full personal name are separated into syllables that constitute the word. The VLSP 2016 NER data also consists of gold POS and chunking tags as [reconfirmed by VLSP 2016 organizers](https://drive.google.com/file/d/1XzrgPw13N4C_B6yrQy_7qIxl8Bqf7Uqi/view?usp=sharing). This scheme results in an unrealistic scenario for a pipeline evaluation: 
  * The standard annotation for Vietnamese word segmentation and POS tagging forms each full name as a word token, thus all   word segmenters have been trained to output a full name as a word and all POS taggers have been trained to assign a POS label to the entire full-name.
  * Gold POS and chunking tags are NOT available in a real-world application.
* For a realistic scenario, contiguous syllables constituting a full name are merged to form a word. Then,  POS tags are predicted by using our tagging component. The results are as follows:

<table>
    <tr>
    <td><b>Model<b></td>
    <td><b>F1</td>
    <td><b>Speed</td>
  </tr>
  <tr>
    <td>VnCoreNLP</td>
    <td><b>88.55</td>
    <td><b>18k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF</td>
    <td>86.48</td>
    <td> 2.8k</td>
  </tr>
   <tr>
    <td>BiLSTM-CRF + CNN-char</td>
    <td>88.28</td>
    <td> 1.8k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + LSTM-char</td>
    <td>87.71</td>
    <td> 1.3k</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + predicted POS</td>
    <td>86.12</td>
    <td> _ </td>
  </tr>
   <tr>
    <td>BiLSTM-CRF + CNN-char + predicted POS </td>
    <td>88.06</td>
    <td> _</td>
  </tr>
  <tr>
    <td>BiLSTM-CRF + LSTM-char + predicted POS</td>
    <td>87.43</td>
    <td> _ </td>
  </tr>
</table>

* Here, for VnCoreNLP, we include the time POS tagging takes in the speed.
* See paper [1] for more details.

### Dependency parsing

* The last 1020 sentences of the [benchmark Vietnamese dependency treebank VnDT](http://vndp.sourceforge.net) are used for test, while the remaining 9k+ sentences are used for training & development. LAS and UAS scores are computed on all
tokens (i.e. including punctuation). 

<table>
  <tr>
    <th colspan="2"><b>Model</b></th>
    <th> <b>LAS</b> (%)</th>
    <th><b>UAS</b> (%)</th>
    <th><b>Speed</th>
  </tr>
  <tr>
    <td rowspan="5">Gold POS</td>
    <td>VnCoreNLP</td>
    <td><b>73.39</td>
    <td>79.02</td>
    <td>_</td>
  </tr>
  <tr>
    <td>BIST-bmstparser</td>
    <td>73.17</td>
    <td><b>79.39</td>
    <td>_</td>
  </tr>
  <tr>
    <td>BIST-barchybrid</td>
    <td>72.53</td>
    <td>79.33</td>
    <td>_</td>
  </tr>
  <tr>
    <td>MSTparser</td>
    <td>70.29</td>
    <td>76.47</td>
    <td>_</td>
  </tr>
  <tr>
    <td>MaltParser</td>
    <td>69.10</td>
    <td>74.91</td>
    <td>_</td>
  </tr>
  <tr>
    <td rowspan="2">Predicted POS</td>
    <td>VnCoreNLP</td>
    <td><b>70.23</td>
    <td>76.93</td>
    <td><b>8k</td>
  </tr>
  <tr>
    <td>jPTDP</td>
    <td>69.49</td>
    <td><b>77.68</td>
    <td>700</td>
  </tr>
</table>

* See paper [1] for more details.
