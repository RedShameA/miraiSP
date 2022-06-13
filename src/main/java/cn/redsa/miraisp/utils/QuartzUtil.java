package cn.redsa.miraisp.utils;

import cn.hutool.core.util.ObjectUtil;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.*;
import java.util.stream.Stream;

@Component
public class QuartzUtil {
    @Resource
    EntityManager entityManager;

    public JobDataMap getJobDataMap(String jobGroup, String jobName){
        String nativeSql = "SELECT JOB_DATA FROM qrtz_job_details WHERE JOB_GROUP = ? AND JOB_NAME = ?";
        Query nativeQuery = entityManager.createNativeQuery(nativeSql);
        nativeQuery.setParameter(1,jobGroup);
        nativeQuery.setParameter(2,jobName);

        byte[] singleResult = (byte[]) nativeQuery.getSingleResult();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(singleResult);

        Object obj;
        try {
            ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
            obj = in.readObject();
        }catch (Exception e){
            return null;
        }
        return (JobDataMap)obj;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateJobDataMap(String jobGroup, String jobName, JobDataMap jobDataMap){
        String nativeSql = "UPDATE qrtz_job_details SET JOB_DATA = ? WHERE JOB_GROUP = ? AND JOB_NAME = ?";
        Query nativeQuery = entityManager.createNativeQuery(nativeSql);
        nativeQuery.setParameter(1,jobDataMap);
        nativeQuery.setParameter(2,jobGroup);
        nativeQuery.setParameter(3,jobName);

        nativeQuery.executeUpdate();
    }
}
