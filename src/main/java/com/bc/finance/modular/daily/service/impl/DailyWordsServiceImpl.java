package com.bc.finance.modular.daily.service.impl;

import cn.hutool.core.util.XmlUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bc.finance.common.exception.business.BusinessException;
import com.bc.finance.common.helper.PageHelper;
import com.bc.finance.common.utils.IntegerUtils;
import com.bc.finance.common.utils.ObjectId;
import com.bc.finance.common.utils.StringUtils;
import com.bc.finance.modular.daily.entity.DailyWords;
import com.bc.finance.modular.daily.mapper.DailyWordsMapper;
import com.bc.finance.modular.daily.service.IDailyWordsService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 单词表 服务实现类
 * </p>
 *
 * @author pcc
 * @since 2022-08-23
 */
@Service
public class DailyWordsServiceImpl extends ServiceImpl<DailyWordsMapper, DailyWords> implements IDailyWordsService {

    @Resource
    DailyWordsMapper mapper;


    @Override
    public List<Map> page(IPage page, Map param) {

        return mapper.page(page, param);
    }


    @Override
    public boolean save(DailyWords words) {

        try {
            URL url = new URL("http://dict.youdao.com/fsearch?client=deskdict&keyfrom=chrome.extension&q=" + words.getWord() + "&pos=-1&doctype=xml&xmlVersion=3.2&dogVersion=1.0&vendor=unknown&appVer=3.1.17.4208&le=eng");
            InputStreamReader reader = new InputStreamReader(url.openStream(), "utf-8");
            StringBuilder builder = new StringBuilder();
            char[] chars = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(chars)) > -1) {
                builder.append(chars, 0, bytesRead);
            }
            reader.close();
            Document document = XmlUtil.parseXml(builder.toString());
            Element root = document.getDocumentElement();
            System.out.println("================================================================================");
            System.out.println("xml String: \n"+builder.toString());
            String symbol = XmlUtil.elementText(root, "us-phonetic-symbol");
            System.out.println("us-phonetic-symbol: "+symbol);
            words.setPronounce(symbol);
            Element ele = XmlUtil.getElementByXPath("/yodaodict/custom-translation/translation/content", document);
            words.setExplain(ele.getTextContent());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if(StringUtils.isBlank(words.getId())) {
            this.insert(words);
        } else {
          this.update(words);
        }
        return true;
    }


    @Override
    public void insert(DailyWords words) {

        DailyWords temp = this.getByWord(words.getWord());
        if(temp!=null) {
            throw new BusinessException("该单词已经添加过, 请勿重复添加");
        }

        words.setId(ObjectId.getString());
        words.setCrtTime(LocalDateTime.now());

        super.save(words);
    }


    @Override
    public void update(DailyWords words) {

        super.updateById(words);
    }


    @Override
    public DailyWords getByWord(String word) {

        return mapper.getByWord(word);
    }


    @Override
    public void imports(List<DailyWords> words) {
        List<String> wordKeys = words.stream().map(e -> e.getWord()).collect(Collectors.toList());
        QueryWrapper query = new QueryWrapper();
        query.in("word", wordKeys);
        List<DailyWords> list = mapper.selectList(query);
        List<String> existsWords = list.stream().map(e -> e.getWord()).collect(Collectors.toList());
        List<DailyWords> insertList = words.stream().filter(e -> !existsWords.contains(e.getWord())).collect(Collectors.toList());
        this.insertBatch(insertList);
    }


    @Override
    public void insertBatch(List<DailyWords> insertList) {
        LocalDateTime now = LocalDateTime.now();
        for (DailyWords words : insertList) {
            words.setId(ObjectId.getString());
            words.setCrtTime(now);
        }
        super.saveBatch(insertList);
    }

    @Override
    public void placementById(String id) {
        DailyWords dailyWords = this.getById(id);
        DailyWords words = new DailyWords();
        words.setId(id);
        words.setLevel(IntegerUtils.notNullDefault(dailyWords.getLevel(), 0)+1);
        this.updateById(words);
    }

    @Override
    public void oneClickPlacement() {
        Page page = PageHelper.defaultPage();
        List<Map> list = this.page(page, new HashMap());
        List<DailyWords> collect = list.stream().map(e -> {
            DailyWords dailyWords = new DailyWords();
            dailyWords.setId(e.get("id").toString());
            dailyWords.setLevel(dailyWords.getLevel()==null? 10: dailyWords.getLevel()+10);
            return dailyWords;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }
}
